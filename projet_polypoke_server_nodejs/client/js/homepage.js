var objet_dresseur = sessionStorage.getItem("infos_joueur")
console.log("objet_dresseur=",objet_dresseur)

if (objet_dresseur !== null) {
    
    display_local_datetime()

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(display_current_forecast);
    }

    setInterval(display_local_datetime, 500);
    setInterval(function () { 
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(display_current_forecast);
        }

        console.log("REFRESH CURRENT FORECAST")
    }, 600000); //refresh toutes les 10 minutes 1800000

    console.log("object not undefined")
    objet_dresseur = JSON.parse(objet_dresseur);
    console.log(typeof objet_dresseur);
    document.getElementById("hello_msg").innerText = " Salut à toi " + objet_dresseur.pseudo + ", bienvenue à l'Académie des Dresseurs et bienvenue sur ton espace personnel POLYPOKE !\n\n Sur cette page tu pourras y retrouver : ton profil Dresseur avec un aperçu de tes informations personnelles et de tes exploits ainsi que ton Pokedex avec les pokémons que tu as trouvé. \n\n Bonne visite !";

} 
else {
    window.location.href = "/client/html/connection.html";  //redirection vers la homage
}

function display_current_forecast(position) {

    var lat = position.coords.latitude;
    var long = position.coords.longitude;

    console.log("lat=", lat, "long=", long)

    fetch('/W_API/'+lat+'/'+long, {
        method: "GET",
    })
    .then(response => response.json()) 
    .then(response => {
        console.log(response)

        var id_weather = response.id;
        var id_icon_to_display; //COPYRIGHTS https://github.com/Makin-Things/weather-icons

        switch(true) {

            case id_weather >= 200 && id_weather <= 232:
                id_icon_to_display = "icon1";
                break;

            case id_weather >= 300 && id_weather <= 321:
                id_icon_to_display = "icon2";
                break;

            case id_weather >= 500 && id_weather <= 504:
                id_icon_to_display = "icon3";
                break;

            case id_weather == 511:
                id_icon_to_display = "icon4";
                break;
            
            case id_weather >= 520 && id_weather <= 531:
                id_icon_to_display = "icon5";
                break;

            case id_weather >= 600 && id_weather <= 622:
                id_icon_to_display = "icon6";
                break;
        
            case id_weather >= 701 && id_weather <= 781:
                id_icon_to_display = "icon7";
                break;
            
            case id_weather == 800:
                id_icon_to_display = "icon8";
                break;
            
            case id_weather == 801:
                id_icon_to_display = "icon9";
                break;
            
            case id_weather == 801:
                id_icon_to_display = "icon10";
                break;

            case id_weather >= 803 && id_weather <= 804:
                id_icon_to_display = "icon11";
                break;

        }

        document.getElementById("main").innerHTML = '<img src="' + '/resources/icons_forecast/' + id_icon_to_display + '.svg" alt="METEO ICON" width="100" height="75">' + 
        '<p>' + response.main + '</p>';
        document.getElementById("description").innerText = response.description; 
        document.getElementById("temperature").innerText = response.temperature + "°C"; 
        document.getElementById("wind-speed").innerText = " Vitesse du vent : " + response.vitesse_vent + " km/h"; 
        document.getElementById("city").innerText = response.ville ; 
        document.getElementById("lat_long").innerText = "Latitude : " + lat.toFixed(6) + " DD \n" + "Longitude : " + long.toFixed(6) + " DD \n"  ; 

    })        
    .catch((error) => {     
    });
}

function display_local_datetime() {
    var t = new Date(); 
    t.UTC
    document.getElementById("localdate").innerHTML = (t.toLocaleDateString()); 
    document.getElementById("localtime").innerHTML = (t.toLocaleTimeString()); 
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