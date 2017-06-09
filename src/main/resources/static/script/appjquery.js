/**
 * 
 */
function Contact(contactId, prenom, nom, numero) {
    this.contactId = contactId;
    this.prenom = prenom;
    this.nom = nom;
    this.numero = numero;
}



$('#styleGeneral').on('change', function() {
    $('body').css('background-color', this.value);
})

$('#listeContacts').on('change', function() {
    if ($('#listeContacts').val() != null) {
        fetchContact($('#listeContacts').val());
    }
})

$('#btnClearAlterFields').on('click', function() {
    $('#alterPrenom').val('');
    $('#alterNom').val('');
    $('#alterNumero').val('');
    $('#listeContacts').val('default').change();
})

$('#SelectAll').on('click', function() {
    $('.checkboxDelete').each(function() {
        $(this).prop('checked', true);
    });
})

$('#UnselectAll').on('click', function() {
    $('.checkboxDelete').each(function() {
        $(this).prop('checked', false);
    });
})

$('#multiDelete').on('click', function() {
    var allVals = [];
    $('#checkboxes :checked').each(function() {
        allVals.push($(this).val());
    });
    console.log(allVals);
    $.ajax({ // appel à la fonction qui va envoyer le contact au serveur
        url: ('http://localhost:8080/contact/'),
        type: 'DELETE',
        data: JSON.stringify(allVals),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function() {
            console.log("OK Delete");
        }
    });
})

$('#getAll').on('click', function() {
    fetchAllContacts();
})

$('#btnModifyContact').on('click', function() {
    var updatedContact = new Contact($('#listeContacts').val(), $('#alterPrenom').val(), $('#alterNom').val(), $('#alterNumero').val());
    $.ajax({ // appel à la fonction qui va envoyer le contact au serveur
        url: ('http://localhost:8080/contact/'),
        type: 'PUT',
        data: JSON.stringify(updatedContact),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function() {
            console.log("OK");
            $('#alterPrenom').val('');
            $('#alterNom').val('');
            $('#alterNumero').val('');
            $('#listeContacts').val('default').change();
        }
    });
})

function fetchContact(id) {
    var url = ('http://localhost:8080/contact/id-' + id);
    $.getJSON(url, function(contact) {
        $('#alterPrenom').val(contact.prenom);
        $('#alterNom').val(contact.nom);
        $('#alterNumero').val(contact.numero);
    });
}

function fetchAllContacts() {
    var url = ('http://localhost:8080/contact/');
    $.getJSON(url, function(contacts) {
        remplirListeDelete(contacts)
        remplirListeModify(contacts)
    });
}

function remplirListeDelete(contacts) { // affichage des contacts dans une liste à puces
    $('#checkboxes').html(''); // vide la liste à puces avant de la remplir.
    var innerTable = '<tr><th></th><th>Firstname</th><th>Lastname</th><th>Phone number</th></tr>';
    $(innerTable).appendTo($('#checkboxes'));

    if (contacts.length == 0) {
        $('#affichageContacts').hide();
    } else {
        contacts.forEach(function(contact) { // itérer sur la collection pour remplir compléter le html
            //  générant la liste à puces
            var line = '<tr>';
            line += ('<td><input type="checkbox" value="' + contact.contactId + '"/></td>');
            line += ('<td>' + contact.prenom + '</td><td>' + contact.nom + '</td><td>' + contact.numero + '</td>');
            line += '</tr>';
            $(line).appendTo($('#checkboxes'));
        }, this);
        $('</table>').appendTo($('#checkboxes'));
    }

}

function remplirListeModify(contacts) { // affichage des contacts dans une liste déroulante
    $('#listeContacts').find('option').remove(); // Vider la liste déroulante avant de la remplir
    $('#listeContacts').append('<option disabled selected value> -- select a contact -- </option>');

    contacts.forEach(function(contact) { // itérer sur la collection pour remplir la liste déroulante
        var value = contact.contactId;
        var text = (contact.prenom + ' ' + contact.nom + ' [' + contact.numero + ']');
        $('#listeContacts').append('<option value="' + value + '" >' + text + '</option>');
    }, this);

}