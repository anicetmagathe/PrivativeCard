package core.model.entity

data class Categorie(
    val id: Int,
    val name: String,
    val description: String = "",
    val price: Double,
    val currency: Currency = Currency(),
    val sector: Sector,
    val available: Long
)
