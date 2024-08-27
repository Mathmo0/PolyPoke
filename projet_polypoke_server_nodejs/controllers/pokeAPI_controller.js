const Pokemon = require("../objects/Pokemon");
const filestream = require('fs');

/// DONE AND TESTED
exports.get_pokemon = async (req, res) => {

    var query = req.params["arg"];

    fetch("https://pokeapi.co/api/v2/pokemon/"+query)  
        .then((response) => response.json())  
        .then((response) => {
            var pokemon = new Pokemon(
                response['id'], 
                response['name'], 
                response['types'][0]['type']['name'], 
                response['species']['name'], 
                response['sprites']['front_default'], 
                response['height'],
                response['weight'],
                response['stats'][0]['base_stat'], //HP
                response['stats'][1]['base_stat'], //ATTAQUE
                response['stats'][3]['base_stat'], //ATTAQUE SPE
                response['stats'][2]['base_stat'], //DEFENSE
                response['stats'][4]['base_stat'], //DEFENSE SPE
                response['stats'][5]['base_stat'] //VITESSE
            );
            res.send(pokemon);
        })
        .catch((error) => {
            console.log("ERROR : ROUTE GET POKEMON FROM POKE API ", error);
            res.sendStatus(404);
        });
  };


/// DONE AND TESTED
exports.get_all_pokemon = async (req, res) => {

    var liste_pokemons = []

    //On récupère la liste de tous nos pokémons
    for (var index = 1; index < 152; index++) {

        var pokemon = await fetch("https://pokeapi.co/api/v2/pokemon/"+index.toString())  
        .then((response) => response.json())  
        .then((response) => {
            var temp = new Pokemon(
                response['id'], 
                response['name'], 
                response['types'][0]['type']['name'], 
                response['species']['name'], 
                response['sprites']['front_default'], 
                response['height'],
                response['weight'],
                response['stats'][0]['base_stat'], //HP
                response['stats'][1]['base_stat'], //ATTAQUE
                response['stats'][3]['base_stat'], //ATTAQUE SPE
                response['stats'][2]['base_stat'], //DEFENSE
                response['stats'][4]['base_stat'], //DEFENSE SPE
                response['stats'][5]['base_stat'] //VITESSE
            );
            return temp;
        })
        .catch((error) => {
            console.log('ERROR : ROUTE GET ALL POKEMON FROM POKE API AT %s : %s', index,  error);
        });
        console.log(pokemon);
        liste_pokemons.push(pokemon);
        console.log("%i", index)
    }

    var path_liste_pokemons = "./data/pokemons.json";
    filestream.writeFile(path_liste_pokemons, JSON.stringify(liste_pokemons), 
    (err) => err && console.error(err));

    res.send(liste_pokemons);
  };