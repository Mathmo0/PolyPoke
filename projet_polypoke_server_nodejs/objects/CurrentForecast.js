class CurrentForecast {

    constructor(id, ville, temperature, main, description, vitesse_vent) {
        this.id = id; // pour gérer les icônes !
        this.ville = ville; // London
        this.temperature = temperature; // °C
        this.main = main; // Vent
        this.description = description; // Quelques rafales de vent
        this.vitesse_vent = vitesse_vent; // km/h

    }

    toJSON () {
        return {
            'id': this.id,
            'ville': this.ville,
            'temperature': this.temperature,
            'main': this.main,
            'description' : this.description,
            'vitesse_vent': this.vitesse_vent,
        };
      }


}

module.exports = CurrentForecast;