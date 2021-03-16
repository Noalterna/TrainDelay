package com.example.traindelay.repository

import com.example.traindelay.api.RetrofitInstance
import com.example.traindelay.model.Route
import com.example.traindelay.model.Station

class Repository {
    suspend fun getStations(): List<Station> = RetrofitInstance.retrofitService.getStations()
    suspend fun getRoutes(from: String, to: String): List<Route> = RetrofitInstance.retrofitService.getRoutes(from, to)
}
