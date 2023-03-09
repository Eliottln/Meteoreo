package com.iut.meteoreo.extensions

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.timestampToHour(): String {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    return DateTimeFormatter.ofPattern("HH:mm", Locale("fr")).format(date)
}

fun Long.timestampToDate(): String {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    return DateTimeFormatter.ofPattern("dd MMMM", Locale("fr")).format(date)
}