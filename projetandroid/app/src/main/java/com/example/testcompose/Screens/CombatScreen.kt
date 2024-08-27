package com.example.testcompose.Screens


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun CombatScreen(navController: NavController, pokemonName : String) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ){
        AsyncImage(model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
            contentDescription = "ditto",
            modifier = Modifier
                .size(150.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = pokemonName, modifier= Modifier.size(150.dp))
    }
}