package core.model.entity

data class Categorie(
    val id: Int,
    val name: String,
    val price: Long,
    val sector: Sector,
    val available: Int
)
