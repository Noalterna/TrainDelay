package com.example.traindelay.utils

data class Resource<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        fun <T> success(data: T?):Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(msg: String, data: T?): Resource<T>{
            return Resource(Status.ERROR, data, msg)
        }

    }

}