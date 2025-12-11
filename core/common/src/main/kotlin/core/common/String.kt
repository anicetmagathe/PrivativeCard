package core.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(format: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse(this, formatter)
}

fun String.upperCaseFirst(): String {
    return split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}

fun String.takeOrEmpty(prefix: String = " "): String {
    return takeIf { it.isNotEmpty() }
        ?.let { "${prefix}${it.upperCaseFirst()}" }.orEmpty()
}