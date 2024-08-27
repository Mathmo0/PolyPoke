const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
    res.sendFile("connection.html", { root: "./client/html/" });  //redirection vers la page detail
});

module.exports = router;