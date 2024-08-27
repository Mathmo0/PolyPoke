package com.example.testcompose.ui.theme


sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Inscription : Routes("Inscription")

    object mapscreen : Routes("MapScreen")
}