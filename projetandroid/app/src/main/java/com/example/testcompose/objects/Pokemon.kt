package com.example.testcompose.objects

import java.io.Serializable

data class Pokemon(
    val id_pokemon: Int = 0,
    val nom: String = "",
    val type: String = "",
    val espece: String = "",
    val sprite: String = "",
    val taille: Int = 0,
    val poids: Int = 0,
    val hp: Int = 0,
    val attaque: Int = 0,
    val attaque_spe: Int = 0,
    val defense: Int = 0,
    val defense_spe: Int = 0,
    val vitesse: Int = 0
) : Serializable
