package com.iut.meteoreo.data

data class Measure(
    val id: Int,
    val stationId: Int,
    val date: String,
    val temperature: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val airPressure: Double,
    val humidity: Double,
    val uvValue: Double
)
