package com.example.testcompose.InterfacesAPI

import com.example.testcompose.objects.AnswerServer
import com.example.testcompose.objects.Dresseur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT


interface InterfaceDRESSEUR {

    @FormUrlEncoded
    @POST("/DRESSEUR/CONNECTION")
    fun BUILDER_CONNECTION_DRESSEUR(
        @Field("mail") mail: String,
        @Field("mdp") mdp: String
    ): Call<Dresseur> //mdp sera à HASHER !!!!!

    @FormUrlEncoded
    @POST("/DRESSEUR/CREATE")
    fun BUILDER_CREATE_DRESSEUR(
        @Field("mail") mail: String,
        @Field("pseudo") pseudo: String,
        @Field("mdp") mdp: String
    ): Call<AnswerServer> //mdp sera à HASHER !!!!!

    @PUT("/DRESSEUR/UPDATE&SAVE")
    fun BUILDER_UPDATE_SAVE_DRESSEUR(
        @Body() body: Dresseur
    ): Call<AnswerServer>

    companion object {
        val URL = "http://10.0.2.2:8080/"
        var dresseurService: InterfaceDRESSEUR? = null
        fun getInstance(): InterfaceDRESSEUR {
            if (dresseurService == null) {
                dresseurService = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(InterfaceDRESSEUR::class.java)
            }
            return dresseurService!!
        }

    }
}

fun CONNECTION_DRESSEUR(mail : String, mdp : String, callback : Callback<Dresseur> ) {

    val URL = "http://10.0.2.2:8080/"
    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfaceDRESSEUR::class.java)

    val DresseurCall = RetrofitBuilder.BUILDER_CONNECTION_DRESSEUR(mail, mdp)

    DresseurCall.enqueue(callback)
}

fun CREATE_DRESSEUR(mail : String, pseudo : String, mdp : String, callback : Callback<AnswerServer>) {

    val URL = "http://10.0.2.2:8080/"

    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfaceDRESSEUR::class.java)

    val AnswerServerCall = RetrofitBuilder.BUILDER_CREATE_DRESSEUR(mail, pseudo, mdp)

    AnswerServerCall.enqueue(callback)

}

fun UPDATE_SAVE_DRESSEUR(dresseur: Dresseur, callback: Callback<AnswerServer>) {

    val URL = "http://10.0.2.2:8080/"

    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfaceDRESSEUR::class.java)

    val AnswerServerCall = RetrofitBuilder.BUILDER_UPDATE_SAVE_DRESSEUR(dresseur)

    AnswerServerCall.enqueue(callback)

}