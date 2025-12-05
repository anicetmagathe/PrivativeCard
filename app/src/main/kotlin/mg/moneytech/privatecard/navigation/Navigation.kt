package mg.moneytech.privatecard.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import mg.moneytech.privatecard.navigation.page.home.MatchPickPage
import mg.moneytech.privatecard.navigation.page.home.SectorPickPage

@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        viewModel.back()
    }

    when (state.page) {
        Page.Home -> MatchPickPage(modifier = modifier)
        Page.Pick -> SectorPickPage(
            modifier = modifier,
            onBack = viewModel::back
        )
    }
}