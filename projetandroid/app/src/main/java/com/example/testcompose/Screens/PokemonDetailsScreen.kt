package com.example.testcompose.Screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.testcompose.R
import com.example.testcompose.objects.Pokemon
import com.example.testcompose.ui.theme.Bug
import com.example.testcompose.ui.theme.Dark
import com.example.testcompose.ui.theme.Dragon
import com.example.testcompose.ui.theme.Electric
import com.example.testcompose.ui.theme.Fairy
import com.example.testcompose.ui.theme.Fighting
import com.example.testcompose.ui.theme.Fire
import com.example.testcompose.ui.theme.Flying
import com.example.testcompose.ui.theme.Ghost
import com.example.testcompose.ui.theme.Grass
import com.example.testcompose.ui.theme.Ground
import com.example.testcompose.ui.theme.Ice
import com.example.testcompose.ui.theme.Normal
import com.example.testcompose.ui.theme.Poison
import com.example.testcompose.ui.theme.Psychic
import com.example.testcompose.ui.theme.RobotoCondensed
import com.example.testcompose.ui.theme.Rock
import com.example.testcompose.ui.theme.Steel
import com.example.testcompose.ui.theme.*
import java.lang.Math.round
import androidx.compose.ui.res.painterResource
import java.util.Locale

@Composable
fun PokemonDetailsScreen(
    dominantColor: Color,
    pokemon: Pokemon,
    pokemonNom: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp
    ) {
     Box(modifier = Modifier
         .fillMaxSize()
         .background(dominantColor)
         .padding(bottom = 16.dp)
     ){
         PokemonDetailTopSection(
             navController = navController,
             modifier = Modifier
                 .fillMaxWidth()
                 .fillMaxHeight(0.2f)
                 .align(Alignment.TopCenter)
         )
         PokemonDetailsStateWrapper(
             pokemon = pokemon,
             modifier = Modifier
                 .fillMaxSize()
                 .padding(
                     top = topPadding + pokemonImageSize / 2f,
                     start = 16.dp,
                     end = 16.dp,
                     bottom = 16.dp
                 )
                 .shadow(10.dp, RoundedCornerShape(10.dp))
                 .clip(RoundedCornerShape(10.dp))
                 .background(MaterialTheme.colorScheme.surface)
                 .padding(16.dp)
                 .align(Alignment.BottomCenter)
         )
         Box(contentAlignment = Alignment.TopCenter,
             modifier = Modifier
             .fillMaxSize()){
             pokemon.sprite.let{
                 AsyncImage(model = it,
                     contentDescription = pokemon.nom,
                     modifier = Modifier
                         .size(pokemonImageSize)
                         .offset(y = topPadding)
                 )
             }
         }
     }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
){
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable { navController.popBackStack() }
        )
    }
}

@Composable
fun PokemonDetailsStateWrapper(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
){
    PokemonDetailSection(
        pokemon = pokemon,
        modifier = modifier
            .offset(y = (-20).dp)
    )
}

@Composable
fun PokemonDetailSection(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .offset(y = 100.dp)
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = "#${pokemon.id_pokemon} ${pokemon.nom.replaceFirstChar { it.uppercase() }}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
        PokemonTypeSection(type = pokemon.type)
        PokemonDetailDataSection(
            pokemonWeight = pokemon.poids,
            pokemonHeight = pokemon.taille
        )
        PokemonBaseStats(pokemon = pokemon)
    }
}

@Composable
fun PokemonTypeSection(
    type: String,
){
    val typeColor = when((type).lowercase()){
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
        else -> Color.White
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ){
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .background(typeColor)
                .height(35.dp)
        ){
            Text(
                text = type.uppercase(Locale.ROOT),
                style = TextStyle(
                    fontFamily = RobotoCondensed,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 80.dp
){
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInM = remember {
        round(pokemonHeight* 100f) / 1000f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightInM,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue $dataUnit",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
){
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if(animationPlayed){
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ){
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemon: Pokemon,
    animDelayPerItem: Int = 100
){
    val maxBaseStat = 250
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Base Stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(4.dp))
        PokemonStat(
            statName = "HP",
            statValue = pokemon.hp,
            statMaxValue = maxBaseStat,
            statColor = HPColor,
            animDelay = animDelayPerItem * 0
        )
        Spacer(modifier = Modifier.height(8.dp))
        PokemonStat(
            statName = "Attack",
            statValue = pokemon.attaque,
            statMaxValue = maxBaseStat,
            statColor = AtkColor,
            animDelay = animDelayPerItem * 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        PokemonStat(
            statName = "Defense",
            statValue = pokemon.defense,
            statMaxValue = maxBaseStat,
            statColor = DefColor,
            animDelay = animDelayPerItem * 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        PokemonStat(
            statName = "Sp. Attack",
            statValue = pokemon.attaque_spe,
            statMaxValue = maxBaseStat,
            statColor = SpAtkColor,
            animDelay = animDelayPerItem * 3
        )
        Spacer(modifier = Modifier.height(8.dp))
        PokemonStat(
            statName = "Sp. Defense",
            statValue = pokemon.defense_spe,
            statMaxValue = maxBaseStat,
            statColor = SpDefColor,
            animDelay = animDelayPerItem * 4
        )
        Spacer(modifier = Modifier.height(8.dp))
        PokemonStat(
            statName = "Speed",
            statValue = pokemon.vitesse,
            statMaxValue = maxBaseStat,
            statColor = SpdColor,
            animDelay = animDelayPerItem * 5
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}