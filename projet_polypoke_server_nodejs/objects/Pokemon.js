class Pokemon {

    constructor(id_pokemon, nom, type, espece, sprite, taille, poids, hp, attaque, attaque_spe, defense, defense_spe, vitesse) {
        this.id_pokemon = id_pokemon;
        this.nom = nom;
        this.type = type;
        this.espece = espece;
        this.sprite = sprite; //lien vers un png
        this.taille = taille;
        this.poids = poids;
        this.hp = hp;
        this.attaque = attaque;
        this.attaque_spe = attaque_spe;
        this.defense = defense;
        this.defense_spe = defense_spe;
        this.vitesse = vitesse;
    }

    toJSON () {
        return {
          'id_pokemon': this.id_pokemon,
          'nom': this.nom,
          'type': this.type,
          'espece': this.espece,
          'sprite': this.sprite,
          'taille': this.taille,
          'poids': this.poids,
          'hp': this.hp,
          'attaque': this.attaque,
          'attaque_spe': this.attaque_spe,
          "defense": this.defense,
          'defense_spe': this.defense_spe,
          'vitesse': this.vitesse,
        };
      }


}

module.exports = Pokemon;