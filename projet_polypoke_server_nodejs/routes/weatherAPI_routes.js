//// ROUTES : WEATHER API
/// ----------------------------------------------------------------------------------------

const weatherAPI_controller = require("../controllers/weatherAPI_controller");
const express = require('express');
const router = express.Router();

/// GET CURRENT FORECAST
/// DONE AND TESTED
router.get("/:lat/:long", weatherAPI_controller.get_current_forecast);

module.exports = router;
