package com.example.testcompose.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.testcompose.R
import com.example.testcompose.Screens.models.PokedexListViewModel
import com.example.testcompose.objects.Pokedex
import com.example.testcompose.objects.Pokemon
import com.example.testcompose.ui.theme.RobotoCondensed
import com.example.testcompose.ui.theme.*

@Composable
fun PokedexScreen(
    navController: NavController,
    viewModel: PokedexListViewModel
){
    val pokemonBis = Pokemon(1, "Bulbizarre", "Electric", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        12, 12, 12, 12, 12, 12, 12, 12)
    val pokedex_init = Pokedex(arrayListOf(true, true, true, true))
    val pokemonList : ArrayList<Pokemon> by viewModel.pokemonList.observeAsState(viewModel.pokemonList.value!!)
    val pokedex : Pokedex by viewModel.pokedex.observeAsState(viewModel.pokedex.value!!)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.height(2.dp))
            Image(
                painter = painterResource(id = R.drawable.pokedex_title),
                contentDescription = "pokedex",
                modifier = Modifier
                    .size(150.dp)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                hint = "Recherche...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                viewModel.searchPokemonList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                lateinit var pokemon : Pokemon
                var pokedexPokemon = false

                items(pokemonList.size) { index ->
                    pokemon = pokemonList[index]
                    pokedexPokemon = pokedex.content?.get(pokemon.id_pokemon - 1) ?: false

                    PokemonRow(
                        pokemon = pokemon,
                        isDecouvert = pokedexPokemon,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonRow(
    pokemon: Pokemon,
    isDecouvert: Boolean,
    navController: NavController
){
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    //do a switch case to display pokemon type
    val dominantColor = when((pokemon.type).lowercase()){
        "grass" -> Grass
        "fire" -> Fire
        "water" -> Water
        "bug" -> Bug
        "normal" -> Normal
        "poison" -> Poison
        "electric" -> Electric
        "ground" -> Ground
        "fairy" -> Fairy
        "fighting" -> Fighting
        "psychic" -> Psychic
        "rock" -> Rock
        "ghost" -> Ghost
        "ice" -> Ice
        "dragon" -> Dragon
        "dark" -> Dark
        "steel" -> Steel
        "flying" -> Flying
        else -> defaultDominantColor
    }
    if(isDecouvert)
    {
        Box(
            contentAlignment = Center,
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            dominantColor,
                            defaultDominantColor
                        )
                    )
                )
                .clickable {
                    var color_int = dominantColor.toArgb()
                    navController.navigate("pokemon_detail_screen/${color_int}/${pokemon.id_pokemon}")
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = pokemon.sprite, contentDescription = pokemon.nom,
                    modifier = Modifier
                        .size(250.dp)
                        .align(CenterHorizontally),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pokemon.nom,
                    style = TextStyle(
                        fontFamily = RobotoCondensed,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
    else{
        Box(
            contentAlignment = Center,
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF272727),
                            defaultDominantColor
                        )
                    )
                )
                .clickable {
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = pokemon.sprite, contentDescription = pokemon.nom,
                    modifier = Modifier
                        .size(250.dp)
                        .align(CenterHorizontally),
                    colorFilter = ColorFilter.tint(Color.Black, blendMode = BlendMode.SrcIn)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "?????",
                    style = TextStyle(
                        fontFamily = RobotoCondensed,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterHorizontally)
                    )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplayed by remember{
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier){
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isNotEmpty()
                }
        )
        if(isHintDisplayed){
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}





