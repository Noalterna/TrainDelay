package com.example.traindelay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.traindelay.repository.Repository

class RouteViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RouteViewModel(repository) as T
    }
}