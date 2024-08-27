package com.example.testcompose.Screens.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.objects.Pokedex
import com.example.testcompose.objects.Pokemon

class PokedexListViewModel: ViewModel(){

    var pokemonList = MutableLiveData<ArrayList<Pokemon>>()
    var pokedex = MutableLiveData<Pokedex>()
    private var cachedPokemonList = ArrayList<Pokemon>()

    init {
        var pokemon1 = Pokemon(1, "Bulbizarre", "Electric", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            12, 12, 120, 120, 120, 120, 120, 120)
        var pokemon2 = Pokemon(2, "Herbizarre", "Normal", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
            12, 12, 120, 120, 120, 120, 120, 120)
        var pokemon3 = Pokemon(2, "Florizar", "Grass", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
            12, 12, 120, 120, 120, 120, 120, 120)
        var pokemon4 = Pokemon(2, "salameche", "Fire", "pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
            12, 12, 120, 120, 120, 120, 120, 120)

        //add pokemon in pokemonList
        pokemonList.value = arrayListOf(pokemon1, pokemon2, pokemon3, pokemon4)

        //add pokemon in pokedex
        pokedex.value = Pokedex(arrayListOf(true, false, false, true))
    }

    fun setData(dresseur: Dresseur, list_pokemon: ArrayList<Pokemon>){
        pokedex.value = dresseur.pokedex
        pokemonList.value = list_pokemon
        cachedPokemonList = list_pokemon
    }

    fun searchPokemonList(query: String){
        if(query.isEmpty()){
            pokemonList.value = cachedPokemonList
        }
        val result = cachedPokemonList.filter {
            it.nom.contains(query.trim(), ignoreCase = true) || it.id_pokemon.toString() == query.trim()
        }
        pokemonList.value = result.let { ArrayList(it) }
    }
}