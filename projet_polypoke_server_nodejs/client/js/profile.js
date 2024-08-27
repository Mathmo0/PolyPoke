var objet_dresseur = sessionStorage.getItem("infos_joueur")
console.log("objet_dresseur=",objet_dresseur)

if (objet_dresseur !== null) {
    
    objet_dresseur = JSON.parse(objet_dresseur)

    var date_c_c = (new Date(objet_dresseur['date_c_c'])).toLocaleDateString();

    var nb_pokemon_pokedex = (objet_dresseur['pokedex']['content'].filter(trouve => trouve == true)).length;

    console.log("object not undefined");
    document.getElementById("icon_dresseur").innerHTML = '<img src="' + '/resources/dresseur_profile.png" alt="DRESSEUR PROFILE" width="150" height="150">';
    document.getElementById("pseudo").innerHTML = '<p>' + objet_dresseur['pseudo'] + '</p>';
    document.getElementById("date_c_c").innerHTML = '<p> Depuis le ' + date_c_c + '</p>';
    document.getElementById('pokedex_infos').innerHTML = '<p>' + nb_pokemon_pokedex + ' pokémons attrapés sur ' + objet_dresseur['pokedex']['content'].length + '</p>';
    document.getElementById('km').innerHTML = '<p>' +  objet_dresseur['km_parcourus'] + ' km parcourus </p>';
    document.getElementById('id').innerHTML = '<p> Id : ' +  objet_dresseur['id_dresseur'] + '</p>';
    document.getElementById('mail').innerHTML = '<p> Mail : ' +  objet_dresseur['mail'] + '</p>';

} 
else {
    window.location.href = "/client/html/connection.html";  //redirection vers la homage
}



function disconnect() {

    var raw = JSON.stringify(objet_dresseur)

    //SAVE OBJET DRESSEUR
    fetch('/DRESSEUR/UPDATE&SAVE/', {
        method: "PUT",
        headers: {"Content-type": "application/json"},
        body: raw,
        redirect: 'follow'
    })
    .then(response => response.json()) 
    .then(response => {
        console.log(response)
        //RETIRER L'OBJET DU NAVIGATEUR
        window.sessionStorage.setItem('infos_joueur', null);
        //RETOUR VERS PAGE DE CONNEXION
        window.location.href = "/client/html/log-off.html";  //redirection vers la hompage
    })        
    .catch((error) => {
        alert("Erreur de sauvegarde !!!!", error)
    });

}