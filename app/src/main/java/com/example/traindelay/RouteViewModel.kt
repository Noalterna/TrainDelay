package com.example.traindelay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traindelay.model.Route
import com.example.traindelay.repository.Repository
import com.example.traindelay.utils.ErrorHandler
import com.example.traindelay.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RouteViewModel(private val repository: Repository): ViewModel() {
    val listOfRoutes = MutableLiveData<Resource<List<Route>>>()
    private val exceptionHandler = CoroutineExceptionHandler{_, exception ->
        listOfRoutes.postValue(Resource.Error(ErrorHandler.getError(exception)))
        Log.e("WyjatekRouteVM",exception.toString())
    }
    fun getRoutes(from:String, to: String){
        viewModelScope.launch(exceptionHandler) {
                val listResult = repository.getRoutes(from, to)
                listOfRoutes.postValue(Resource.Success(listResult))
        }
    }
}