package com.example.traindelay.repository

import com.example.traindelay.api.RetrofitInstance
import com.example.traindelay.model.Station


//to moze nie dzialac z tymi listami

class Repository {
    suspend fun getStations(): List<Station> {
        return RetrofitInstance.retrofitService.getStations()
    }
}
