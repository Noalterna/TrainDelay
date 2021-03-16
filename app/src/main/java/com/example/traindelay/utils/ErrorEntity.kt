package com.example.traindelay.utils

sealed class ErrorEntity {
    object Network: ErrorEntity()
    object NotFound: ErrorEntity()
    object ServiceUnavailable: ErrorEntity()
    object Unknown: ErrorEntity()
}