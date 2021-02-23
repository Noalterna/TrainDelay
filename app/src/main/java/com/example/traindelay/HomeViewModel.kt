package com.example.traindelay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traindelay.model.Station
import com.example.traindelay.repository.Repository
import com.example.traindelay.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repository: Repository): ViewModel() {
    val stations = MutableLiveData<Resource<List<Station>>>()
    private val exceptionHandler = CoroutineExceptionHandler{_, exception ->
        stations.postValue(Resource.error(exception.message.toString(), null))
    }


    fun getStations(){
        viewModelScope.launch(exceptionHandler) {
                val listOfStations = repository.getStations()
                stations.postValue(Resource.success(listOfStations))
        }
    }

}