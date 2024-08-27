package com.example.testcompose

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ZoomControls
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.testcompose.InterfacesAPI.CONNECTION_DRESSEUR
import com.example.testcompose.InterfacesAPI.GET_CURRENT_FORECAST
import com.example.testcompose.InterfacesAPI.InterfacePOKEMON
import com.example.testcompose.InterfacesAPI.InterfaceRESOURCES
import com.example.testcompose.InterfacesAPI.UPDATE_SAVE_DRESSEUR
import com.example.testcompose.Screens.DresseurScreen
import com.example.testcompose.Screens.InscriptionScreen
import com.example.testcompose.Screens.LoginScreen
import com.example.testcompose.Screens.models.PokedexListViewModel
import com.example.testcompose.objects.AnswerServer
import com.example.testcompose.objects.CurrentForecast
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.objects.Pokedex
import com.example.testcompose.objects.Pokemon
import com.example.testcompose.ui.theme.Routes
import com.example.testcompose.viewModel.DresseurViewModel
import com.example.testcompose.viewModel.PokemonViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.common.cache.LoadingCache
import com.google.common.io.Resources
import com.google.googlejavaformat.java.ImportOrderer
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs
import kotlin.random.Random


class MainActivity : ComponentActivity() {


    val viewModelDresseur by viewModels<DresseurViewModel>()
    val pokemonViewModel by viewModels<PokemonViewModel>()
    val ALLpokemonViewModel by viewModels<PokemonViewModel>()
    val REQUEST_LOCATION_PERMISSION = 1
    var longitude: Double = 0.0
    var latitude: Double = 0.0


    val dresseur = Dresseur()
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val dresseur = Dresseur()

        try{
            val dresseur = (getIntent().getExtras()?.getSerializable("dresseurCombat")) as Dresseur
            if(dresseur.id_dresseur != "")
            {
                viewModelDresseur.setData(dresseur)
            }
        }
        catch (e:Exception)
        {
        }

        // Vérifiez si l'autorisation d'accéder à la localisation a déjà été accordée
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Si l'autorisation est déjà accordée, récupérez les coordonnées GPS
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            // Récupérez la longitude et la latitude
            if (lastKnownLocation != null) {
                longitude = lastKnownLocation.longitude
            }
            if (lastKnownLocation != null) {
                latitude = lastKnownLocation.latitude
            }
        } else {
            // Si l'autorisation n'est pas encore accordée, demandez-la à l'utilisateur
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }

        //Get all pokemon from the server using pokemonViewModel
        ALLpokemonViewModel.get_all_pokemons()

        lateinit var dresseurViewModel: DresseurViewModel

        //FONCTIONNEL
        var DresseurTest: Dresseur?
        var pokedex: Pokedex?
        DresseurTest = Dresseur()


        setContent {
            val navController = rememberNavController()
            var currentForecast: CurrentForecast = CurrentForecast()
            dresseurViewModel = ViewModelProvider(this).get(DresseurViewModel::class.java)


            NavHost(
                navController = navController,
                startDestination = "loginScreen"
            ) {
                composable("loginScreen") {
                    if(viewModelDresseur.dresseurConnecte.value?.id_dresseur ?: "" == ""){
                        LoginScreen(navController = navController, viewModel = dresseurViewModel)
                    }
                    else{
                        dresseurViewModel.setData(viewModelDresseur.dresseurConnecte.value!!)
                        navController.navigate(Routes.mapscreen.route)
                    }

                }
                composable("MapScreen")
                 {
                    DresseurTest = dresseurViewModel.dresseurConnecte.value
                    GET_CURRENT_FORECAST(
                        latitude,
                        longitude,
                        object : retrofit2.Callback<CurrentForecast> {
                            override fun onResponse(
                                call: Call<CurrentForecast>,
                                response: Response<CurrentForecast>
                            ) {
                                if (response.body() != null && response.isSuccessful == true) {
                                    //currentForecast = response.body()!!
                                    Log.d("REQ GET CURRENT WEATHER FROM SERVER", "objet météo reçu")
                                }
                            }

                            override fun onFailure(call: Call<CurrentForecast>, t: Throwable) {
                                Log.d(
                                    "FAILED REQ GET CURRENT WEATHER FROM SERVER",
                                    "ECHEC REQUETE " + t
                                ) //échec de connexion (mauvais login ou serveur hs)
                            }
                        })
                    Column() {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.polypoke_logo),
                                contentDescription = "Pokeball",
                                modifier = Modifier
                                    .size(50.dp)
                            )

                            Text(text = currentForecast.ville)

                            Text(text = currentForecast.temperature.toString() + " °C")
                        }

                        val location = LatLng(latitude, longitude)

                        //MapScreen(location, DresseurTest, pokemonViewModel, navController)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.Green.copy(alpha = 0.1f))
                                .weight(1f, true),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(painter = painterResource(id = R.drawable.pokedex),
                                contentDescription = "Pokeball",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        val navigatePokedex =
                                            Intent(this@MainActivity, PokedexActivity::class.java)
                                                .putExtra("dresseur", DresseurTest)
                                                .putExtra(
                                                    "AllPokemon",
                                                    ALLpokemonViewModel.allPokemons
                                                )
                                        startActivity(navigatePokedex)
                                    }
                            )

                            Image(painter = painterResource(id = R.drawable.fight),
                                contentDescription = "Pokeball",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        var pokemon1 = Pokemon(
                                            1,
                                            "Bulbizarre",
                                            "Electric",
                                            "pikachu",
                                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                                            12,
                                            12,
                                            120,
                                            120,
                                            120,
                                            120,
                                            120,
                                            120
                                        )
                                        val navigate = Intent(
                                            this@MainActivity,
                                            Combat::class.java
                                        ).putExtra("pokemon", pokemon1)
                                        startActivity(navigate)
                                    }
                            )

                            Image(painter = painterResource(id = R.drawable.trainer000),
                                contentDescription = "Dresseur",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        val navigate = Intent(
                                            this@MainActivity,
                                            DresseurActivity::class.java
                                        ).putExtra("dresseur", DresseurTest)
                                            .putExtra("AllPokemon", ALLpokemonViewModel.allPokemons)
                                        startActivity(navigate)
                                    }
                            )

                        }

                    }


                }
                composable("Inscription") {
                    InscriptionScreen(navController = navController)
                }
            }
        }
    }

    @Composable
    fun MapScreen(localisation: LatLng, dresseur: Dresseur?, pokemonViewModel: PokemonViewModel, navController: NavController) {
        val context = LocalContext.current
        val width = 100
        val height = 200

        val imageDresseur = BitmapFactory.decodeResource(context.resources, R.drawable.charac)
        val dresseurBitmap = Bitmap.createScaledBitmap(imageDresseur, width, height, false)

        pokemonViewModel.get_nb_random_pokemons(10)

        GoogleMap(
            cameraPositionState = CameraPositionState(CameraPosition(localisation, 18f, 0f, 0f)),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true
            ),
            modifier = Modifier
                .fillMaxHeight(0.9f)
        ) {
            Marker(
                state = MarkerState(position = localisation),
                icon = BitmapDescriptorFactory.fromBitmap(dresseurBitmap)
            )

            AddPokemons(
                localisation = localisation,
                pokemons = pokemonViewModel.pokemonListResponse,
                dresseur = dresseur,
                navController= navController
            )
        }
    }

    @Composable
    fun pokemon(localisation: LatLng, pokemon: Pokemon, dresseur: Dresseur?, navController: NavController) {
        val context = LocalContext.current
        val height = 200
        val width = 200

        //Get image du pokemon
        val idPokemon = pokemon.id_pokemon
        val IdImg = "p" + idPokemon.toString()
        val idRes = context.resources.getIdentifier(IdImg, "drawable", context.packageName)
        val bitmapdraw = BitmapFactory.decodeResource(context.resources, idRes)
        val smallMarker = Bitmap.createScaledBitmap(bitmapdraw, width, height, false)

        val randLat = Random.nextDouble(-1.0, 1.0) / 500
        val randLong = Random.nextDouble(-1.0, 1.0) / 500
        val randomLocation =
            LatLng(localisation.latitude + randLat, localisation.longitude + randLong)
        val locationState = (MarkerState(position = randomLocation))

        Marker(
            state = locationState,
            onClick = {
                if (abs(localisation.latitude - randomLocation.latitude) <= 0.0009 && abs(
                        localisation.longitude - randomLocation.longitude
                    ) <= 0.0009
                ) {
                    val navigate = Intent(context, Combat::class.java).putExtra("pokemon", pokemon)
                        .putExtra("dresseur", dresseur)
                    startActivity(context, navigate, null)
                }
                false
            },
            icon = BitmapDescriptorFactory.fromBitmap(smallMarker)
        )

    }

    @Composable
    fun AddPokemons(localisation: LatLng, pokemons: List<Pokemon>, dresseur: Dresseur?, navController: NavController) {
        pokemons.forEach() {
            pokemon(localisation = localisation, pokemon = it, dresseur = dresseur, navController = navController)
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(
            text = "Hello $name!"
        )
    }
}