const path = require('path');

const bodyParser = require('body-parser');
const express = require('express');
const app = express();
const port = 8080;

const router_index = require('./routes/index_routes')
const router_poke_API = require('./routes/pokeAPI_routes')
const router_weather_API = require('./routes/weatherAPI_routes')
const router_pokemon = require('./routes/pokemon_routes')
const router_dresseur = require('./routes/dresseur_routes')

//A placer avant les routes, très important pour les requêtes (notamment POST pour URLENCODED)
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
//app.use(express.raw());
app.use(bodyParser.raw({inflate:true, limit: '100kb', type: 'application/json'}));

app.use('/', router_index);
app.use('/P_API', router_poke_API);
app.use('/W_API', router_weather_API);
app.use('/POKEMON', router_pokemon);
app.use('/DRESSEUR', router_dresseur);
app.use('/client', express.static(path.join(__dirname, 'client')))
app.use('/resources', express.static(path.join(__dirname, 'resources')))

app.listen(port); //pour démarrer l'application

module.exports = app;