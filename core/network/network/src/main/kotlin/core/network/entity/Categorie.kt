@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)

package core.network.entity

import core.model.entity.Sector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import core.model.entity.Categorie as CategorieEntity


@Serializable
data class Categorie(
    @SerialName("Libelle") val label: String,

    @SerialName("Description") val description: String,

    @SerialName("Prix") val price: String,

    @SerialName("Devise") val currency: String,
)

fun Categorie.asExternal(): CategorieEntity {
    return CategorieEntity(
        id = 0,
        name = label,
        price = price.toDouble(),
        sector = Sector(0),
        available = 10
    )
}