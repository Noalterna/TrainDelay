package com.example.traindelay.model

data class Route(
    val arrival: String,
    val delay: String,
    val departure: String,
    val id: Int,
    val name: String,
    val operator: String,
    val relation: List<String>
)