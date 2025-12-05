package core.common

import java.text.NumberFormat
import java.util.Date
import java.util.Locale

fun Long.format(): String {
    return numberFormat.format(this)
}

fun Long.toDate(): Date {
    return Date(this)
}

private val numberFormat = NumberFormat.getInstance(Locale.FRANCE)