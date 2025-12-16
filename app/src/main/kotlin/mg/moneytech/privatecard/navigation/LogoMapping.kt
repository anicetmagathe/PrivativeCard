package mg.moneytech.privatecard.navigation

import core.designsystem.component.PCIcons

fun logoForCategorie(categorieId: Int): Int {
    return categorieLogoMapping[categorieId]
        ?: throw RuntimeException("Unknown categorie id: $categorieId")
}

private val categorieLogoMapping = mapOf(
    1 to PCIcons.stadiumSeat,
    2 to PCIcons.stadiumSeat2,
    3 to PCIcons.stadiumSeat3,
)