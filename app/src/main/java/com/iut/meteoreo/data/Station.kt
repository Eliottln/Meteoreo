package com.iut.meteoreo.data

data class Station(
    val id: Int? = -1,
    val name: String = "No name",
    val measures: List<Measure>? = null
)
