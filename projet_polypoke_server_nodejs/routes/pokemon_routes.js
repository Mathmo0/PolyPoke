//// ROUTES : POKEMON
/// ----------------------------------------------------------------------------------------

const pokemon_controller = require("../controllers/pokemon_controller");
const express = require('express');
const router = express.Router();

/// GET ALL POKEMON
/// DONE AND TESTED
router.get("/", pokemon_controller.get_all_pokemon);

/// GET nb RANDOM POKEMON
router.get("/RANDOM/:nb", pokemon_controller.get_nb_random_pokemon);

module.exports = router;
