const filestream = require('fs');

//DONE AND TESTED
exports.get_all_pokemon = async (req, res) => {
    console.log(" LISTE POKEMONS ENVOYEE ");
    res.sendFile("/pokemons.json", { root: './data/' });
};

//DONE AND TESTED
exports.get_nb_random_pokemon = async (req, res) => {

    var nb = req.params['nb'];

    if (nb > 151) { //impossible de réclamer plus de 151 pokémons
        nb = 151;
    }
    else if (nb < 0) {
        nb = 0;
    }

    var liste_nb_random = Array.from(Array(151).keys());
    var liste_pokemons_random = [];
    var liste_pokemons = JSON.parse(filestream.readFileSync("./data/pokemons.json", (err) => err && console.error(err)));

    liste_nb_random.sort(() => (Math.random() > .5) ? 1 : -1);
    liste_nb_random.sort(() => (Math.random() > .1) ? 1 : -1);
    liste_nb_random.sort(() => (Math.random() < .9) ? 1 : -1);

    for (var count = 0; count < nb; count++) {
        liste_pokemons_random.push(liste_pokemons[liste_nb_random[count]])
    }

    console.log(nb, "RANDOM POKEMON SENT FROM SERVER TO CLIENT...")

    res.send(JSON.stringify(liste_pokemons_random))
};