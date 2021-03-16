package com.example.traindelay.utils

sealed class Resource<T> {
        data class Success<T>(val data: T): Resource<T>(){}
        /*fun <T> error(msg: String, data: T?): Resource<T>{
            return Resource(Status.ERROR, data, msg)
        }*/
        data class Error<T>(val error: ErrorEntity): Resource<T>(){}
}