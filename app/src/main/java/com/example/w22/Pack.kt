package com.example.w22

data class Pack(
    val name: String,
    val description: String,
    val pricePerHour: Double,
    val additionalHourPrice: Double,
    val features: List<String>
)

