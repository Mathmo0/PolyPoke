//// ROUTES : POKEAPI
/// ----------------------------------------------------------------------------------------

const pokeAPI_controller = require("../controllers/pokeAPI_controller");
const express = require('express');
const router = express.Router();

/// GET POKEMON
router.get("/GET_POKEMON_API/:arg", pokeAPI_controller.get_pokemon);

/// GET ALL POKEMON
router.get("/GET_ALL_POKEMON_API/", pokeAPI_controller.get_all_pokemon);
module.exports = router;