package com.example.traindelay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traindelay.model.Route
import com.example.traindelay.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class RouteViewModel(private val repository: Repository): ViewModel() {
    val listOfRoutes = MutableLiveData<List<Route>>()

    fun getRoutes(from:String, to: String){
        viewModelScope.launch {
            try {
                val listResult = repository.getRoutes(from, to)
                listOfRoutes.value = listResult
            }
            catch (e: Exception){
                Log.e("Failure", e.message.toString())
            }
        }
    }
}