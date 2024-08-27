const Dresseur = require("../objects/Dresseur");
const filestream = require('fs');
const { json } = require("express");

exports.get_dresseur_by_id = async (req, res) => {
  //TO DO
};

exports.get_dresseur_by_pseudo = async (req, res) => {
  //TO DO
};

/// DONE, TESTED AND USED
function mail_verification(liste_credentials, mail) {
  for (var index in liste_credentials) {
    if (liste_credentials[index]['mail'] == mail) {
      return true;
    }
  }
  return false;
}

/// DONE, TESTED AND USED
function connection_verification(liste_credentials, mail, mdp) {
  console.log("CONNECTION VERIFICATION...")
  for (var index in liste_credentials) {
    if (liste_credentials[index]['mail'] == mail && liste_credentials[index]['mdp'] == mdp) {
      console.log("CONNECTION VERIFICATION DONE -> DRESSEUR ID", liste_credentials[index]['id'], " ALLOWED...")
      return liste_credentials[index]['id'];
    }
  }
  console.log("CONNECTION VERIFICATION DONE -> CREDENTIALS UNKNOWN...")
  return "-1";
}

/// DONE AND TESTED
exports.create_dresseur = (req, res) => {

  console.log("POST BODY : ", req.body);

  var mail = req.body.mail;
  var liste_credentials = filestream.readFileSync("./data/credentials.json", (err) => err && console.error(err));

  try {
    liste_credentials = JSON.parse(liste_credentials);
  }
  catch(e) {
    liste_credentials = []
  }

  if (mail_verification(liste_credentials, mail)) {

    var status = "ERREUR";
    var message = " Cette adresse email est déjà associée à un compte ";

    answer = {
      "type" : status,
      "message" : message,
    }
    console.log(answer);
    res.send(JSON.stringify(answer));
    // res.send('ERROR : Cette adresse email est déjà associée à un compte.');
  }
  else {
    
    var pseudo = req.body.pseudo;
    var mdp = req.body.mdp; //hashed
    var date_c_c = Date.now();
    var dresseur = new Dresseur(pseudo, mail, mdp, date_c_c);
    console.log(dresseur);
    var path_file_dresseur = './data/'+dresseur.id_dresseur.toString()+'.json';
    filestream.writeFile(path_file_dresseur, JSON.stringify(dresseur),  (err) => err && console.error(err));

    var credentials = {
      id: dresseur.id_dresseur.toString(),
      mail: mail,
      mdp: mdp,
    };

    liste_credentials.push(credentials);
    var path_file_credentials = './data/credentials.json';
    filestream.writeFile(path_file_credentials, JSON.stringify(liste_credentials),  (err) => err && console.error(err));

    console.log(dresseur);

    var status = "REUSSITE";
    var message = "Salut " + dresseur.pseudo + " bienvenue à l'Académie des dresseurs de Pokémon ! Tu peux maintenant te connecter et partir à l'aventure !";

    answer = {
      "type" : status,
      "message" : message,
    }

    console.log(answer);
    res.send(JSON.stringify(answer));

    // res.send("Salut " + dresseur.pseudo + " bienvenue à l'Académie des dresseurs de Pokémon ! Tu peux maintenant te connecter et partir à l'aventure !");
  }
};

/// DONE AND TESTED
exports.connection_dresseur = (req, res) => {

  var mail = req.body.mail;
  var mdp = req.body.mdp;

  console.log("MAIL TAPE=",mail, "MDP TAPE=", mdp);

  var liste_credentials = filestream.readFileSync("./data/credentials.json", (err) => err && console.error(err));

  var connection_answer;

  try {
    liste_credentials = JSON.parse(liste_credentials);
    connection_answer = connection_verification(liste_credentials, mail, mdp);
  }
  catch(e) {}

  if (connection_answer != "-1") {
    var dresseur_object = filestream.readFileSync("./data/"+connection_answer+".json", (err) => err && console.error(err));
    res.send(JSON.parse(dresseur_object));
  } 
  else {
    res.send("ERREUR DE CONNEXION : Il y a visiblement un problème avec ce que tu as tapé, réessaye ! ");
  }
};

/// DONE AND TESTED
exports.update_save_dresseur = (req, res) => {
  var data = req.body;
  console.log(data);

  var status = ""
  var message = ""

  if (data === undefined) {
    status = "ERREUR";
    message = "Échec de la sauvegarde sur le serveur...";
  }
  else {
    var path_file_dresseur = './data/'+data['id_dresseur'].toString()+'.json';
    filestream.writeFile(path_file_dresseur, JSON.stringify(data),  (err) => err && console.error(err));
    status = "REUSSITE";
    message = "Donnée bien sauvegardée sur le serveur !";  
  }

  answer = {
    "type" : status,
    "message" : message,
  }

  console.log(answer);

  res.send(JSON.stringify(answer));
};