var objet_dresseur = sessionStorage.getItem("infos_joueur");
console.log("objet_dresseur=",objet_dresseur);

if (objet_dresseur !== null) {

    document.getElementById("log-off").addEventListener("click", disconnect);

    fetch('/POKEMON/', {
        method: "GET",
        headers: {"Content-type": "application/json"},
        redirect: 'follow'
    })
    .then(response => response.json()) 
    .then(response => {

        objet_dresseur = JSON.parse(objet_dresseur);    

        var output_html = '<table id="table_pokedex"> <tbody> <tr>';

        objet_dresseur['pokedex']['content'][1] = true
        objet_dresseur['pokedex']['content'][3] = true
        objet_dresseur['pokedex']['content'][16] = true
        objet_dresseur['pokedex']['content'][2] = true
        objet_dresseur['pokedex']['content'][10] = true
        objet_dresseur['pokedex']['content'][7] = true
        objet_dresseur['pokedex']['content'][30] = true
        objet_dresseur['pokedex']['content'][89] = true
        objet_dresseur['pokedex']['content'][66] = true

        for (var index=0; index < 151; index++) {

            var category = "";
            var color_espece = "";

            if (objet_dresseur['pokedex']['content'][index] == true) {
                category = "shown";

                switch(true) {

                    case response[index]['type'] == "grass":
                        color_espece = "#7AC74C"
                        break;

                    case response[index]['type'] == "normal":
                        color_espece = "#A8A77A";
                        break;   

                    case response[index]['type'] == "fire":
                        color_espece = "#EE8130";
                        break;
                    
                    case response[index]['type'] == "water":
                        color_espece = "#6390F0";
                        break; 
                   
                    case response[index]['type'] == "electric":
                        color_espece = "#F7D02C";
                        break;
                        
                    case response[index]['type'] == "ice":
                        color_espece = "#96D9D6";
                        break;

                    case response[index]['type'] == "fighting":
                        color_espece = "#C22E28";
                        break;
                            
                    case response[index]['type'] == "poison":
                        color_espece = "#A33EA1";
                        break;              
                
                    case response[index]['type'] == "ground":
                        color_espece = "#E2BF65";
                        break;         
                
                    case response[index]['type'] == "flying":
                        color_espece = "#A98FF3";
                        break;         
                    
                    case response[index]['type'] == "psychic":
                        color_espece = "#F95587";
                        break;     

                    case response[index]['type'] == "bug":
                        color_espece = "#A6B91A";
                        break;         
                    
                    case response[index]['type'] == "rock":
                        color_espece = "#B6A136";
                        break;
    
                    case response[index]['type'] == "ghost":
                        color_espece = "#735797";
                        break;    

                    case response[index]['type'] == "dragon":
                        color_espece = "#6F35FC";
                        break;
                        
                    case response[index]['type'] == "dark":
                        color_espece = "#705746";
                        break;
                
                    case response[index]['type'] == "steel":
                        color_espece = "#B7B7CE";
                        break;
                
                    case response[index]['type'] == "fairy":
                        color_espece = "#D685AD";
                        break;  
                
                }



            }
            else if(objet_dresseur['pokedex']['content'][index] == false) {
                category = "hidden";
            }

            if (index % 10 == 0) {
                output_html += '</tr>';
                output_html += '<tr>';
            }

            output_html += '<td  style="background-color:' + color_espece + ';">' + '<img class="'+category+'"src="/resources/pokemon_sprites/p' + (index+1) + '.png" width="120" height="120">' + 
            '<p class="' + category +'" >' + (response[index]['nom']).toUpperCase() + ' </p> <p class="' + category +'">' + response[index]['type'] + '</p> </td>';
        }

        output_html += '</tbody></table>';

        document.getElementById("table_pokedex").innerHTML = output_html;

    })        
    .catch((error) => {
        alert("Erreur de récupération des pokémons depuis le serveur !!!!");
    });
    
} 
else {
    window.location.href = "/client/html/connection.html";  //redirection vers la homage
}

function disconnect() {

    var raw = JSON.stringify(objet_dresseur);

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
        alert("Erreur de sauvegarde des informations...");
    });

}
