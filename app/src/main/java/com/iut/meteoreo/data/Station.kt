package com.iut.meteoreo.data

data class Station(
    val name: String = "No name",
    val measures: List<Measure>? = null
)
