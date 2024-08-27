class Pokedex {

    constructor() {

        //Pokedex n'est qu'une map des id de nos pokémons avec une valeur : true ou false pour la découverte
        var array = [];
        for (var id = 1; id < 152; id++) {
            array.push(false)
        }
        this.content_pokemon = array;
    }

    appendPokemon(index, decouvert) {
        this.content_pokemon[index] = decouvert;
    }

    toJSON() {
        return {
            "content": this.content_pokemon,
        };
    }

}

module.exports = Pokedex;