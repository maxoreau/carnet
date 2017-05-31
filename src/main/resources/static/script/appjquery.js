/**
 * 
 */
function Contact(contactId, prenom, nom, numero) {
    this.contactId = contactId;
    this.prenom = prenom;
    this.nom = nom;
    this.numero = numero;
}

var contactsListeDeroulante = [];
var selectedContact = null;


$('#styleGeneral').on('change', function () {
    $('body').css('background-color', this.value);
})

$('#listeContacts').on('change', function () {
    console.log('listeContacts.onchange');
    fetchContact($('#listeContacts').val());
})

$('#btnClearAlterFields').on('click', function() {
    selectedContact = null;    
    $('#alterPrenom').val('');
    $('#alterNom').val('');
    $('#alterNumero').val('');
})

$('#btnModifyContact').on('click', function() {
    
})

function fetchContact(id) {
    var url = ('http://localhost:8080/contact/id' + id);
    $.getJSON(url, function (contact) {
        $('#alterPrenom').val(contact.prenom);
        $('#alterNom').val(contact.nom);
        $('#alterNumero').val(contact.numero);
    });
}


function remplirListeAPucesContacts(contacts) { // affichage des contacts dans une liste à puces
    $('#affichageContacts').html(''); // vide la liste à puces avant de la remplir.
    var listeAPuces = '<p>Contacts</p><ul>'; //initialise le html d'une liste à puce.

    contacts.forEach(function (contact) { // itérer sur la collection pour remplir compléter le html
        //  générant la liste à puces
        listeAPuces += ('<li>' + contact.prenom + ' ' + contact.nom + ' [ ' + contact.numero + ']</li>');
    }, this);
    listeAPuces += '</ul>'; // finalise la liste à puces
    $('#affichageContacts').append(listeAPuces); // ajoute la liste à puces à la div.

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


function alterContact() {
    if (selectedContact != null) {
        updateContactServer(selectedContact);
    }
}

function updateContactServer(contact) {
    var updatedContact = new Contact(contact.contactId, $('#modifyPrenom').val(), $('#modifyNom').val(), $('#modifyNumero').val());
    $.ajax({ // appel à la fonction qui va envoyer le contact au serveur
        url: ('http://localhost:8080/restex/rest/contacts/' + contact.nom),
        type: 'PUT',
        data: JSON.stringify(updatedContact),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        async: true,
        success: function (msg) {
            searchByString(''); //prendre en charge la réponse si besoin
            $('#modifyPrenom').val('');
            $('#modifyNom').val('');
            $('#modifyNumero').val('');
            selectedContact = null;
        }
    });
}


function clearAlterFields() {
    $('#modifyPrenom').val('');
    $('#modifyNom').val('');
    $('#modifyNumero').val('');
    selectedContact = null;
    remplirListeDeroulanteContacts(contactsListeDeroulante);
}