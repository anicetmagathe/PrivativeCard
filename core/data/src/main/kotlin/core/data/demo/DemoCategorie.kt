package core.data.demo

import core.model.entity.Categorie
import core.model.entity.Sector

object DemoCategorie {
    val categories = listOf(
        Categorie(
            id = 1,
            name = "Tribune principale",
            price = 1000.0,
            sector = Sector(120),
            available = 150
        ),
        Categorie(
            id = 2,
            name = "Virage supporters",
            price = 900.0,
            sector = Sector(450),
            available = 100
        ),
        Categorie(
            id = 3,
            name = "Places VIP",
            price = 1200.0,
            sector = Sector(15),
            available = 50
        ),
    )
}