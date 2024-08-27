const Pokedex = require('./Pokedex.js');
const Pokemon = require('./Pokemon.js');
const { v4: uuidv4 } = require('uuid');

class Dresseur {

    constructor(pseudo, mail, mdp, date_creation_compte) {
        this.id_dresseur = uuidv4(); //générer auto un identifiant unique
        this.pseudo = pseudo;
        this.mail = mail;
        this.mdp = mdp; //déjà hashé (sécurité)
        this.pokedex = new Pokedex();
        this.pokemon_ami = null;
        this.km_parcourus = 0.0;
        this.date_c_c = date_creation_compte;
    }

    toJSON () {
        return {
            'id_dresseur': this.id_dresseur,
            'pseudo': this.pseudo,
            'mail': this.mail,
            'mdp' : this.mdp,
            'pokedex': this.pokedex,
            'pokemon_ami': this.pokemon_ami,
            'km_parcourus': this.km_parcourus,
            'date_c_c': this.date_c_c,
        };
      }


}

module.exports = Dresseur;