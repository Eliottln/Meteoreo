package com.iut.meteoreo.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.timestampToDate(): String {
    val date = Date(this)
    val sdf = SimpleDateFormat("EEE", Locale.FRENCH)
    return sdf.format(date)
}