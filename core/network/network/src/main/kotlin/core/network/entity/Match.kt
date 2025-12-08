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

    @SerialName("Description") val description: String,

    @SerialName("Categorie") val categories: List<Categorie>
)

fun Match.asExternal(): MatchEntity {
    val clubNames = label.split(" vs ")
    return MatchEntity(
        club1 = Club(name = clubNames[0], 0),
        club2 = Club(name = clubNames[1], 0),
        date = this.dateTime.toLocalDateTime("dd/MM/yyyy HH:mm"),
        description = description,
        categories = categories.map {
            it.asExternal()
        },
        stadium = Stadium(name = stadium, city = "", club = "", capacity = 0)
    )
}