package com.example.testcompose.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcompose.InterfacesAPI.InterfacePOKEMON
import com.example.testcompose.objects.Pokemon
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel(){

    var pokemonListResponse: ArrayList<Pokemon> by mutableStateOf(arrayListOf())
    var allPokemons: ArrayList<Pokemon> by mutableStateOf(arrayListOf())
    var errorMessage: String by mutableStateOf("")

    fun get_nb_random_pokemons(n:Int)
    {
        viewModelScope.launch {
            val interfacePokemon = InterfacePOKEMON.getInstance()
            try{
                val pokemonList = interfacePokemon.BUILDER_GET_NB_RANDOM_POKEMON(n)
                pokemonListResponse = pokemonList
            }
            catch (e:Exception){
                errorMessage = e.message.toString()
            }
        }
    }

    fun get_all_pokemons()
    {
        viewModelScope.launch {
            val interfacePokemon = InterfacePOKEMON.getInstance()
            try{
                val pokemonList = interfacePokemon.BUILDER_GET_ALL_POKEMON()
                allPokemons = pokemonList
            }
            catch (e:Exception){
                errorMessage = e.message.toString()
            }
        }
    }


}