package com.example.traindelay.api

import com.example.traindelay.model.Station
import retrofit2.http.GET

interface ApiInterface {
    @GET("stations")
    suspend fun getStations(): List<Station>
}