package com.example.traindelay.utils

sealed class Resource<T> {
        data class Success<T>(val data: T): Resource<T>(){}
        data class Error<T>(val error: ErrorEntity): Resource<T>(){}
}