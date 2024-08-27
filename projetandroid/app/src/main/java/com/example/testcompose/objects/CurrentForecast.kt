package com.example.testcompose.objects

import java.io.Serializable

data class CurrentForecast(
    val id: Int = 0,
    val description: String = "",
    val main: String = "",
    val temperature: Double = 0.0,
    val ville: String = "",
    val vitesse_vent: Double = 0.0
) : Serializable