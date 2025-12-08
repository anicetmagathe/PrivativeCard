package core.model.entity

import java.time.LocalDateTime

data class Match(
    val club1: Club,
    val club2: Club,
    val date: LocalDateTime,
    val stadium: Stadium,
    val description: String = "",
    val categories: List<Categorie> = emptyList()
)