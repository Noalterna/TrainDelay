package com.example.traindelay.api

import com.example.traindelay.model.Route
import com.example.traindelay.model.Station
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("stations")
    suspend fun getStations(): List<Station>

    @GET("trains?")
    suspend fun getRoutes(
        @Query("from") from: String,
        @Query("to") to:String): List<Route>
}
