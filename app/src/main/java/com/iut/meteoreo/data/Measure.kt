package com.iut.meteoreo.data

data class Measure(
    val timestamp: String? = "",
    val temperature: Double? = null,
    val windSpeed: Double? = null,
    val windDirection: Double? = null,
    val airPressure: Double? = null,
    val humidity: Double? = null,
    val uvValue: Double? = null
)
