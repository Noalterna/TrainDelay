package com.example.traindelay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traindelay.model.Station
import com.example.traindelay.repository.Repository
import com.example.traindelay.utils.ErrorEntity
import com.example.traindelay.utils.ErrorHandler
import com.example.traindelay.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class HomeViewModel(private val repository: Repository): ViewModel() {
    val stations = MutableLiveData<Resource<List<Station>>>()
    private val exceptionHandler = CoroutineExceptionHandler{_, exception ->
        stations.postValue(Resource.Error(ErrorHandler.getError(exception)))
        Log.e("Wyjatek",exception.toString())
    }

    fun getStations(){
        viewModelScope.launch(exceptionHandler) {
                val listOfStations = repository.getStations()
                stations.postValue(Resource.Success(listOfStations))
        }
    }
}