package com.example.testcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.testcompose.InterfacesAPI.UPDATE_SAVE_DRESSEUR
import com.example.testcompose.objects.AnswerServer
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.objects.Pokemon
import com.example.testcompose.ui.theme.Routes
import com.example.testcompose.viewModel.DresseurViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import java.util.concurrent.ThreadLocalRandom

@AndroidEntryPoint
class Combat : ComponentActivity() {

    private lateinit var dresseurViewModel: DresseurViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = (getIntent().getExtras()?.getSerializable("pokemon")) as Pokemon
        val dresseur = (getIntent().getExtras()?.getSerializable("dresseur")) as Dresseur


        dresseurViewModel = ViewModelProvider(this).get(DresseurViewModel::class.java)
        dresseurViewModel.setData(dresseur)

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            val dres : Dresseur by dresseurViewModel.dresseurConnecte.observeAsState(dresseurViewModel.dresseurConnecte.value!!)

            Surface {
                Column(horizontalAlignment= Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green.copy(alpha = 0.3f))
                ) {

                    Text(text = pokemon.nom, fontSize= 30.sp, fontWeight = FontWeight.Bold)

                    AsyncImage(model = pokemon.sprite,
                        contentDescription = "pokemon",
                        modifier = Modifier.size(500.dp))



                    Image(painter = painterResource(id = R.drawable.pokeball),
                        contentDescription = "Pokeball",
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                val random = ThreadLocalRandom
                                    .current()
                                    .nextInt(0, 10)

                                if (random <= 7) {
                                    Toast
                                        .makeText(context, "Bravo ! Pokemon attrapé !", Toast.LENGTH_SHORT)
                                        .show()
                                    if (dres.pokedex!!.content?.get(pokemon.id_pokemon - 1) ?: null == false) {
                                        dres.pokedex!!.content?.set(pokemon.id_pokemon - 1, true)

                                        UPDATE_SAVE_DRESSEUR(
                                            dres,
                                            object : retrofit2.Callback<AnswerServer> {
                                                override fun onResponse(
                                                    call: retrofit2.Call<AnswerServer>,
                                                    response: Response<AnswerServer>
                                                ) {
                                                    if (response.body() != null && response.isSuccessful == true) {
                                                        var reponse_server =
                                                            response.body()!! //on récupère l'objet
                                                        if (reponse_server.type == "REUSSITE") {
                                                            Log.d(
                                                                "REQ UPDATE&SAVE DRESSEUR SERVER",
                                                                reponse_server.message
                                                            )
                                                        } else if (reponse_server.type == "ERREUR") {
                                                            Log.d(
                                                                "REQ UPDATE&SAVE DRESSEUR SERVER",
                                                                reponse_server.message
                                                            )
                                                        }
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: retrofit2.Call<AnswerServer>,
                                                    t: Throwable
                                                ) {
                                                    Log.d(
                                                        "FAILED REQ UPDATE&SAVE DRESSEUR SERVER",
                                                        "ECHEC REQUETE : " + t
                                                    )
                                                }
                                            })

                                    }

                                    val navigate = Intent(context, MainActivity::class.java).putExtra("dresseurCombat", dres)
                                    ContextCompat.startActivity(context, navigate, null)



                                } else {
                                    Toast
                                        .makeText(context, "Raté ! Essayez encore !", Toast.LENGTH_SHORT)
                                        .show()
                                }


                            }

                    )
                    Text("Clickez sur la pokeball pour attraper " + pokemon.nom + " !")
                }

            }
        }
    }
}