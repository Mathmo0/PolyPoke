package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testcompose.Screens.PokedexScreen
import com.example.testcompose.Screens.PokemonDetailsScreen
import com.example.testcompose.Screens.models.PokedexListViewModel
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.objects.Pokedex
import com.example.testcompose.objects.Pokemon
import com.example.testcompose.ui.theme.JetpackComposePokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexActivity : ComponentActivity() {

    private lateinit var viewModel:PokedexListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dresseur = (getIntent().getExtras()?.getSerializable("dresseur")) as Dresseur
        var pokemon_list = (getIntent().getExtras()?.getSerializable("AllPokemon")) as ArrayList<Pokemon>

        viewModel = ViewModelProvider(this).get(PokedexListViewModel::class.java)
        viewModel.setData(dresseur, pokemon_list)

        setContent {
            JetpackComposePokedexTheme{
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ){
                    composable("pokemon_list_screen"){
                        var pokemonList = ArrayList<Pokemon>()
                        var pokedex = Pokedex(ArrayList<Boolean>())
                        pokedex.content?.add(true)
                        pokedex.content?.add(false)
                        pokedex.content?.add(false)
                        pokedex.content?.add(true)

                        var pokemon1 = Pokemon(1, "Bulbizarre", "Electric", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                            12, 12, 12, 12, 12, 12, 12, 12)
                        var pokemon2 = Pokemon(2, "Herbizarre", "Normal", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                            12, 12, 12, 12, 12, 12, 12, 12)
                        var pokemon3 = Pokemon(2, "Florizar", "Grass", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                            12, 12, 12, 12, 12, 12, 12, 12)
                        var pokemon4 = Pokemon(2, "salameche", "Fire", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                            12, 12, 12, 12, 12, 12, 12, 12)
                        //add pokemon to list
                        pokemonList.add(pokemon1)
                        pokemonList.add(pokemon2)
                        pokemonList.add(pokemon3)
                        pokemonList.add(pokemon4)
                        PokedexScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("pokemon_detail_screen/{dominantColor}/{pokemonId}",
                        arguments = listOf(
                            navArgument("dominantColor"){
                                type = NavType.IntType
                            },
                            navArgument("pokemonId"){
                                type = NavType.IntType
                            }
                        )
                    ){
                        val dominantColor = remember{
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let{ Color(it) } ?: Color.White
                        }
                        val pokemonId = remember {
                            it.arguments?.getInt("pokemonId")
                        }

                        val pokemon = viewModel.pokemonList.value!!.find {
                                pokemon -> pokemon.id_pokemon == pokemonId!!.toInt()
                        }
                        if (pokemon != null) {
                            PokemonDetailsScreen(
                                dominantColor = dominantColor,
                                pokemon = pokemon,
                                pokemonNom = pokemon.nom,
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}