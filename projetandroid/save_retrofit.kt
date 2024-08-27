//FONCTIONNEL
var DresseurTest : Dresseur?
var mail = "salut@gmail.com"
var mdp = "123soleil" //GARDE CA COMME CA ET J'TE GIFLE KILLIAN
CONNECTION_DRESSEUR(mail, mdp, object : retrofit2.Callback<Dresseur> {
            override fun onResponse(call: Call<Dresseur>, response: Response<Dresseur>) {

                if (response.body() != null && response.isSuccessful == true) {
                    DresseurTest = response.body() //on récupère l'objet
                    Log.d("REQ CONNECTION DRESSEUR SERVER", "CONNEXION ACCEPTEE")
                }
            }
            override fun onFailure(call: Call<Dresseur>, t: Throwable) {
                Log.d("FAILED REQ CONNECTION DRESSEUR SERVER", "ECHEC REQUETE : " + t) //échec de connexion (mauvais login ou serveur hs)
            }
        })


//FONCTIONNEL
var reponse_server : AnswerServer
var mail2 = "salut@gmail.com"
var pseudo2 = "jean"
var mdp2 = "123soleil" //GARDE CA COMME CA ET J'TE GIFLE KILLIAN
CREATE_DRESSEUR(mail2, pseudo2, mdp2, object : retrofit2.Callback<AnswerServer> {
            override fun onResponse(call: Call<AnswerServer>,     response: Response<AnswerServer>) {

                if (response.body() != null && response.isSuccessful == true) {
                    reponse_server = response.body()!! //on récupère l'objet

                    if (reponse_server.type == "REUSSITE") {
                        //CREATION DE COMPTE RÉUSSIE
                        Log.d("REQ CREATE DRESSEUR SERVER", reponse_server.message)
                    }
                    else if (reponse_server.type == "ERREUR"){
                        //ECHEC : MAIL DÉJA ASSOCIE A UN COMPTE
                        //CREATION DE COMPTE RÉUSSIE
                        Log.d("REQ CREATE DRESSEUR SERVER", reponse_server.message)
                    }
                }
            }
            override fun onFailure(call: Call<AnswerServer>, t: Throwable) {
                Log.d("FAILED REQ CREATE DRESSEUR SERVER", "ECHEC REQUETE : " + t) //échec de connexion (mauvais login ou serveur hs)
            }
        })


//FONCTIONNEL
GET_ALL_POKEMON(object : retrofit2.Callback<List<Pokemon>> {
    override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.body() != null && response.isSuccessful == true) {
                    var ListePokemons = response.body()
                    Log.d("REQ GET ALL POKEMON FROM SERVER", "Liste reçue")
                }
            }
            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("FAILED REQ GET ALL POKEMON FROM SERVER", "ECHEC REQUETE " + t) //échec de connexion (mauvais login ou serveur hs)
            }
        })

//FONCTIONNEL
var latitude : Double = 0.0
var longitude : Double = 0.0
var currentForecast : CurrentForecast = CurrentForecast()
GET_CURRENT_FORECAST(latitude, longitude, object : retrofit2.Callback<CurrentForecast> {
            override fun onResponse(call: Call<CurrentForecast>, response: Response<CurrentForecast>) {
                if (response.body() != null && response.isSuccessful == true) {
                    currentForecast = response.body()!!
                    Log.d("REQ GET CURRENT WEATHER FROM SERVER", "objet météo reçu")
                }
            }
            override fun onFailure(call: Call<CurrentForecast>, t: Throwable) {
                Log.d("FAILED REQ GET CURRENT WEATHER FROM SERVER", "ECHEC REQUETE " + t) //échec de connexion (mauvais login ou serveur hs)
            }
        })


//FONCTIONNEL
UPDATE_SAVE_DRESSEUR(DresseurTest, object : retrofit2.Callback<AnswerServer> {
                        override fun onResponse(call: Call<AnswerServer>, response: Response<AnswerServer>) {
                            if (response.body() != null && response.isSuccessful == true) {
                                var reponse_server = response.body()!! //on récupère l'objet
                                if (reponse_server.type == "REUSSITE") {
                                    Log.d("REQ UPDATE&SAVE DRESSEUR SERVER", reponse_server.message)
                                }
                                else if (reponse_server.type == "ERREUR"){
                                    Log.d("REQ UPDATE&SAVE DRESSEUR SERVER", reponse_server.message)
                                }
                            }
                        }
                        override fun onFailure(call: Call<AnswerServer>, t: Throwable) {
                            Log.d("FAILED REQ UPDATE&SAVE DRESSEUR SERVER", "ECHEC REQUETE : " + t)
                        }
                    })


//FONCTIONNEL
var nb : Int = 15
GET_NB_RANDOM_POKEMON(nb, object : retrofit2.Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.body() != null && response.isSuccessful == true) {
                    var ListePokemons = response.body()
                    Log.d("REQ GET NB RANDOM POKEMON FROM SERVER", "LISTE RECUE")
                }
            }
            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.d("FAILED REQ GET NB RANDOM POKEMON FROM SERVER", "ECHEC REQUETE " + t) //échec de connexion (mauvais login ou serveur hs)
            }
        })