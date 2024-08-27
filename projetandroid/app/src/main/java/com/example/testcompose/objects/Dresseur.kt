package com.example.testcompose.objects

import java.io.Serializable

data class Dresseur(
    val id_dresseur: String = "",
    var pseudo: String = "",
    var mail: String = "",
    var mdp: String = "",
    var pokedex: Pokedex? = null,
    var pokemon_ami: Pokemon? = null,
    var km_parcourus: Double = 0.0,
    val date_c_c: Long? = null,
) : Serializable {

}