//// ROUTES : DRESSEUR
/// ----------------------------------------------------------------------------------------

const dresseur_controller = require("../controllers/dresseur_controller");
const express = require('express');
const router = express.Router();

/// GET DRESSEUR (PAR ID)
/// TODO
router.get("/id/:arg", dresseur_controller.get_dresseur_by_id);

/// GET DRESSEUR (PAR PSEUDO)
/// TODO
router.get("/pseudo/:arg", dresseur_controller.get_dresseur_by_pseudo);

/// CREATE DRESSEUR //POST
/// DONE AND TESTED
router.post("/CREATE/", dresseur_controller.create_dresseur);

/// CONNECTION TO DRESSEUR PROFILE //POST
/// DONE AND TESTED
router.post("/CONNECTION/", dresseur_controller.connection_dresseur);

/// UPDATE AND SAVE DRESSEUR PROFILE FROM ANDROID //PUT
/// DONE AND TESTED
router.put("/UPDATE&SAVE/", dresseur_controller.update_save_dresseur);

module.exports = router;