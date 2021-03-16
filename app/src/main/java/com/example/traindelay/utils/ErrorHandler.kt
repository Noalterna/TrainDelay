package com.example.traindelay.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandler {
    companion object {
        fun getError(throwable: Throwable): ErrorEntity {
            return when (throwable) {
                is IOException -> ErrorEntity.Network
                is HttpException -> {
                    when (throwable.code()) {
                        HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                        HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                        else -> ErrorEntity.Unknown
                    }
                }
                else -> ErrorEntity.Unknown
            }
        }
    }
}