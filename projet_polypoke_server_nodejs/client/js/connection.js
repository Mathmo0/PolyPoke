document.getElementById("connection_form").addEventListener('submit', connect);
var connection_answer = document.getElementById("connection_server_answer")

async function connect(e) {

    e.preventDefault();

    var mail = document.getElementById('mail_form').value;
    var mdp = document.getElementById('passw_form').value;
    console.log(mdp);

    mdp = await sha256(mdp);

    console.log(mdp);

    fetch('/DRESSEUR/CONNECTION/', {
        method: "POST",
        headers: {"Content-type": "application/x-www-form-urlencoded"},
        body: new URLSearchParams({
            'mail': mail,
            'mdp': mdp
          })
    })
    .then(response => response.json()) 
    .then(response => {
        window.sessionStorage.setItem('infos_joueur', JSON.stringify(response));
        window.location.href = "/client/html/homepage.html";  //redirection vers la homage
        console.log(response)
    })        
    .catch((error) => {
        
        connection_answer.innerText = "ERREUR : Mauvaise combinaison mail/mot de passe. Réessaye !"
        console.log('ERREUR : Mauvaise combinaison mail/mot de passe. Réessaye !', error);
    
    });

}

//TO GENERATE SHA256 VALUE
async function sha256(message) {
    // encode as UTF-8
    const msgBuffer = new TextEncoder().encode(message);                    

    // hash the message
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);

    // convert ArrayBuffer to Array
    const hashArray = Array.from(new Uint8Array(hashBuffer));

    // convert bytes to hex string                  
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    return hashHex;
}