@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)

package core.network.entity

import core.common.toLocalDateTime
import core.model.entity.Club
import core.model.entity.Stadium
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import core.model.entity.Match as MatchEntity

@Serializable
data class Match(
    @SerialName("Stade") val stadium: String,

    @SerialName("Date") val dateTime: String,

    @SerialName("Libelle") val label: String,

    @SerialName("Team1") val team1: String,

    @SerialName("LogoTeam1") val logoTeam1: String,

    @SerialName("Team2") val team2: String,

    @SerialName("LogoTeam2") val logoTeam2: String,

    @SerialName("Description") val description: String,

    @SerialName("Categorie") val categories: List<Categorie>
)

fun Match.asExternal(): MatchEntity {
    return MatchEntity(
        club1 = Club(name = team1, logo = 0, logoUrl = logoTeam1),
        club2 = Club(name = team2, logo = 1, logoUrl = logoTeam2),
        date = this.dateTime.toLocalDateTime("dd/MM/yyyy HH:mm"),
        description = description,
        categories = categories.map {
            it.asExternal()
        },
        stadium = Stadium(name = stadium, city = "", club = "", capacity = 0)
    )
}