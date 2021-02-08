package com.example.traindelay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traindelay.model.Station
import com.example.traindelay.repository.Repository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class HomeViewModel(private val repository: Repository): ViewModel() {
    val _response = MutableLiveData<List<Station>>()

    fun getStations(){
        viewModelScope.launch {
            try {
                val listResult = repository.getStations()
                _response.value = listResult
            }
            catch (e: Exception){
                Log.d("Failure", e.message.toString())
            }
        }
    }
}