package com.example.testcompose.InterfacesAPI

import com.example.testcompose.objects.CurrentForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface InterfaceW_API {

    @GET("/W_API/{LAT}/{LONG}")
    fun BUILDER_GET_CURRENT_FORECAST(
        @Path("LAT") latitude : Double,
        @Path("LONG") longitude : Double) : Call<CurrentForecast>
}


fun GET_CURRENT_FORECAST(latitude: Double, longitude: Double, callback: Callback<CurrentForecast> ) {

    val URL = "http://10.0.2.2:8080/"

    val RetrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL)
        .build()
        .create(InterfaceW_API::class.java)

    val CurrentForecastCall = RetrofitBuilder.BUILDER_GET_CURRENT_FORECAST(latitude, longitude)

    CurrentForecastCall.enqueue(callback)

}
