package core.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(format: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse(this, formatter)
}