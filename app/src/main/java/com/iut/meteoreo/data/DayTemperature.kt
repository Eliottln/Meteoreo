package com.iut.meteoreo.data

import java.time.LocalDate

data class DayTemperature(
    val day: LocalDate,
    val maxTemperature: Measure? = null,
    val minTemperature: Measure? = null
)
