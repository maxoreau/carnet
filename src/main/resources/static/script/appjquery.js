/**
 * 
 */
function Contact(contactId, prenom, nom, numero) {
    this.contactId = contactId;
    this.prenom = prenom;
    this.nom = nom;
    this.numero = numero;
}



$('#styleGeneral').on('change', function () {
    $('body').css('background-color', this.value);
})

$('#listeContacts').on('change', function () {
    if ($('#listeContacts').val() != null) {
        fetchContact($('#listeContacts').val());
    }
})

$('#btnClearAlterFields').on('click', function () {
    $('#alterPrenom').val('');
    $('#alterNom').val('');
    $('#alterNumero').val('');
    $('#listeContacts').val('default').change();
})


$('#btnModifyContact').on('click', function () {
    var updatedContact = new Contact($('#listeContacts').val(), $('#alterPrenom').val(), $('#alterNom').val(), $('#alterNumero').val());
    console.log(JSON.stringify(updatedContact));
    $.ajax({ // appel à la fonction qui va envoyer le contact au serveur
        url: ('http://localhost:8080/contact/'),
        type: 'PUT',
        data: JSON.stringify(updatedContact),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function (contacts) {
            remplirListeAPucesContacts(contacts);
            remplirListeDeroulanteContacts(contacts);
            $('#alterPrenom').val('');
            $('#alterNom').val('');
            $('#alterNumero').val('');
            $('#listeContacts').val('default').change();
        }
    });
})

function fetchContact(id) {
    var url = ('http://localhost:8080/contact/id-' + id);
    $.getJSON(url, function (contact) {
        $('#alterPrenom').val(contact.prenom);
        $('#alterNom').val(contact.nom);
        $('#alterNumero').val(contact.numero);
    });
}

function remplirListeAPucesContacts(contacts) { // affichage des contacts dans une liste à puces
    $('#affichageContacts').html(''); // vide la liste à puces avant de la remplir.
    if (contacts.length != 0) {
        var listeAPuces = '<fieldset><legend>Display</legend><ul>'; //initialise le html d'une liste à puce.

        contacts.forEach(function (contact) { // itérer sur la collection pour remplir compléter le html
            //  générant la liste à puces
            listeAPuces += ('<li>' + contact.prenom + ' ' + contact.nom + ' [' + contact.numero + ']</li>');
        }, this);
        listeAPuces += '</ul></fieldset>'; // finalise la liste à puces
        $('#affichageContacts').append(listeAPuces); // ajoute la liste à puces à la div.

    }

}

function remplirListeDeroulanteContacts(contacts) { // affichage des contacts dans une liste déroulante
    $('#listeContacts').find('option').remove(); // Vider la liste déroulante avant de la remplir
    $('#listeContacts').append('<option disabled selected value> -- select a contact -- </option>');

    contacts.forEach(function (contact) { // itérer sur la collection pour remplir la liste déroulante
        var value = contact.contactId;
        var text = (contact.prenom + ' ' + contact.nom + ' : ' + contact.numero);
        $('#listeContacts').append('<option value="' + value + '" >' + text + '</option>');
    }, this);

}