package com.example.testcompose.InterfacesAPI

import com.example.testcompose.objects.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfacePOKEMON {

    @GET("/POKEMON")
    suspend fun BUILDER_GET_ALL_POKEMON() : ArrayList<Pokemon>

    @GET("/POKEMON/RANDOM/{NB}")
    suspend fun BUILDER_GET_NB_RANDOM_POKEMON(
        @Path("NB") nb_pokemons : Int ) : ArrayList<Pokemon>

    companion object{
        val URL = "http://10.0.2.2:8080/"
        var pokemonService:InterfacePOKEMON? = null
        fun getInstance() : InterfacePOKEMON {
            if(pokemonService == null)
            {
                pokemonService = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(InterfacePOKEMON::class.java)
            }
            return pokemonService!!
        }
    }
}

/*fun GET_ALL_POKEMON(callback: Callback<List<Pokemon>>) {

    val URL = "http://10.0.2.2:8080/"

    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfacePOKEMON::class.java)

    val ListePokemonCall = RetrofitBuilder.BUILDER_GET_ALL_POKEMON()

    ListePokemonCall.enqueue(callback)

}*/

/*fun GET_NB_RANDOM_POKEMON(nb_pokemon : Int, callback: Callback<List<Pokemon>>) {

    val URL = "http://10.0.2.2:8080/"

    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfacePOKEMON::class.java)

    val ListePokemonCall = RetrofitBuilder.BUILDER_GET_NB_RANDOM_POKEMON(nb_pokemon)

    ListePokemonCall.enqueue(callback)

}*/