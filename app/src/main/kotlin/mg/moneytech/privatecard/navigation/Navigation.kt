package mg.moneytech.privatecard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mg.moneytech.privatecard.navigation.page.home.Home
import mg.moneytech.privatecard.navigation.page.pick.SectorPickPage

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    SectorPickPage(modifier = modifier)
}