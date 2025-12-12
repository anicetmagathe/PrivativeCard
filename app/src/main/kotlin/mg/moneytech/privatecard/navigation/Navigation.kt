package mg.moneytech.privatecard.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.designsystem.theme.LocalAppTheme
import mg.moneytech.privatecard.navigation.component.PrintView
import mg.moneytech.privatecard.navigation.component.UndismissableDialog
import mg.moneytech.privatecard.navigation.page.home.MatchPickPage
import mg.moneytech.privatecard.navigation.page.home.SectorPickPage

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        viewModel.back()
    }

    CompositionLocalProvider(
        LocalAppTheme provides state.theme
    ) {
        AnimatedContent(state.page) {
            when (it) {
                Page.Home -> MatchPickPage(modifier = modifier, innerPadding = innerPadding)
                Page.Pick -> SectorPickPage(
                    modifier = modifier,
                    innerPadding = innerPadding,
                    onBack = viewModel::back
                )
            }
        }

        if (state.showLatestTicket) {
            state.receiptImage?.let {
                UndismissableDialog {
                    PrintView(
                        painter = BitmapPainter(it.asImageBitmap()),
                        onHide = viewModel::hideReceipt
                    )
                }
            }
        }
    }
}