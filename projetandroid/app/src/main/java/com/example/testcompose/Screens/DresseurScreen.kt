package com.example.testcompose.Screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testcompose.R
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.objects.Pokedex


@Composable
fun ShowInfo(texte: String)
{
    Text(texte, style = TextStyle(Color.White, fontSize=20.sp, fontFamily = FontFamily(Font(R.font.trebuc))))
}

fun CountPokemon(pokedex: Pokedex?): Int {
    var count = 0
    if (pokedex != null) {
        for (elem in pokedex.content!!)
            if (elem)
                count++
    }
    return count
}

@SuppressLint("DiscouragedApi")
@Composable
fun DresseurScreen(navController: NavHostController, dresseur: Dresseur) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Profil Dresseur", style = TextStyle(fontSize = 40.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier.padding(0.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.charac),
                contentDescription = "Personnage",
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
            )

            /*val idPokemon = dresseur.pokemon_ami?.id_pokemon
            val idImg = "p" + idPokemon.toString()
            val idRes = context.resources.getIdentifier(idImg, "drawable", context.packageName)
            Image(
                painter = painterResource(id = idRes),
                contentDescription = "Pokemon ami",
                modifier = Modifier
                    .size(250.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
            )*/
        }


        Spacer(modifier = Modifier.height(20.dp))
        Text(dresseur.pseudo, style = TextStyle(fontSize = 30.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
        //Text("et ${dresseur.pokemon_ami?.nom}", style = TextStyle(fontSize = 20.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.trebuc))))

        /*Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(60.dp, 0.dp, 60.dp, 0.dp)) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Modifier pokemon ami",
                    style = TextStyle(
                        Color.White,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))
                    )
                )
            }
        }*/
        Spacer(modifier = Modifier.height(60.dp))

        val nombrePokemons = CountPokemon(dresseur.pokedex)
        ShowInfo("Adresse email : ${dresseur.mail}")
        ShowInfo("Date de création du compte : ${dresseur.date_c_c}")
        ShowInfo("Distance parcourue : ${dresseur.km_parcourus} km")
        ShowInfo("Pokemons découverts : $nombrePokemons")

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(60.dp, 0.dp, 60.dp, 0.dp)) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Accéder au pokedex",
                    style = TextStyle(
                        Color.White,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
            }
        }
    }
}
