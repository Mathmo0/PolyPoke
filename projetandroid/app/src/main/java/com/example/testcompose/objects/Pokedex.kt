package com.example.testcompose.objects

import java.io.Serializable

data class Pokedex(
    var content: ArrayList<Boolean>? = null
) : Serializable