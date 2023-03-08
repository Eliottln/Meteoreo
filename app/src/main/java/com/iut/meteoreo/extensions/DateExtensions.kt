package com.iut.meteoreo.extensions

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.timestampStartOfDay(): Long {
    return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun LocalDate.timestampNextDay(): Long {
    return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() + 86400000
}

fun LocalDate.toDayOfWeek(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE", Locale("fr"))
    return this.format(formatter)
}