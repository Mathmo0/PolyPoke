package com.example.testcompose.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.ui.theme.Routes


@Composable
fun ScreenMain() {
    val navController = rememberNavController()
    //val viewModel = remember { PokedexListViewModel() }

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            //LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(Routes.Inscription.route) {
            InscriptionScreen(navController = navController)
        }
    }
}


