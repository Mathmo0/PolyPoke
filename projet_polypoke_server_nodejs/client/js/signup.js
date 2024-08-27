document.getElementById("signup_form").addEventListener('submit', signup);
var signup_answer = document.getElementById("signup_server_answer");

async function signup(e) {

    e.preventDefault();

    var mail = document.getElementById('mail_form').value;
    var mdp = document.getElementById('passw_form').value;
    var pseudo = document.getElementById('pseudo_form').value;

    console.log(pseudo, mdp, mail)

    mdp = await sha256(mdp)

    console.log(mdp);

    fetch('/DRESSEUR/CREATE/', {
        method: "POST",
        headers: {"Content-type": "application/x-www-form-urlencoded"},
        body: new URLSearchParams({
            'mail': mail,
            'mdp': mdp,
            'pseudo': pseudo
          })
    })
    .then(response => response.json()) 
    .then(response => {

        console.log(response)

        if (response['type'] == "REUSSITE") {
            signup_answer.innerText = response['message']
            signup_answer.style.color = "green";
            signup_answer.style.fontFamily = "Trebuchet MS";
            signup_answer.style.backgroundColor = "rgba(0, 0, 0, 0.716)";
            console.log("Inscription : réussite")
        }
        else if(response['type'] == "ERREUR") {

            signup_answer.innerText = response['message'];
            signup_answer.style.color = "red";
            signup_answer.style.fontFamily = "Trebuchet MS";
            signup_answer.style.backgroundColor = "rgba(0, 0, 0, 0.716)";
            console.log("Inscription : échec, mail déjà utilisé");
        }

    })        
    .catch((error) => {
        
        signup_answer.innerText = "ERREUR : Erreur de connexion avec le serveur, impossible de vous inscrire. Réessayez ultérieurement.";
        setTimeout(function(){
            signup_answer.innerText = "";
        }, 1500);
        console.log('ERREUR : Erreur de connexion avec le serveur, impossible de vous inscrire. Réessayez ultérieurement."', error);
    
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
