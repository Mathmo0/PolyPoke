const CurrentForecast = require('../objects/CurrentForecast');

var weatherAPI_key = "26ec69bb7e253fbd6c59fa96f1061c7b";
var URLexample = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=26ec69bb7e253fbd6c59fa96f1061c7b";

/// DONE AND TESTED
exports.get_current_forecast = (req, res) => {

    var lat = req.params["lat"];
    var long = req.params["long"];
    console.log("LONG=", long, "LAT=", lat);
    
    fetch("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+long+"&appid="+weatherAPI_key)
    .then((response) => response.json())
    .then((response) => {
        
        console.log(response)

        var id = response['weather'][0]['id'];
        var ville = response['name'];
        var temp = ((response['main']['temp']) - 273.15).toFixed(1); // kelvin to degré celsius
        var main = response['weather'][0]['main'];
        var description = response['weather'][0]['description'];
        var vitesse_vent = ((response['wind']['speed']) * 3.6).toFixed(1); // m.s-1

        var current_forecast = new CurrentForecast(id, ville, temp, main, description, vitesse_vent);

        console.log('REUSSITE : GET CURRENT FORECAST FROM WEATHERAPI DONE');
        
        res.send(current_forecast);
    })
    .catch((error) => {
        console.log('ERROR : ROUTE GET CURRENT FORECAST FROM WEATHERAPI ', error);
        res.sendStatus(404);
    });

}