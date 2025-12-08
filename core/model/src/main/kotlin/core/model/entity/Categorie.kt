package core.model.entity

data class Categorie(
    val id: Int,
    val name: String,
    val price: Double,
    val sector: Sector,
    val available: Int
)
