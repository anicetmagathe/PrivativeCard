package core.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.format(format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)/*.withLocale(Locale.FRANCE)*/
    return format(formatter)
}