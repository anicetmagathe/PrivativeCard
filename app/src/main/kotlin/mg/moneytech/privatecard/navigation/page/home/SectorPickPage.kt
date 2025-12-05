package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.data.demo.DemoMatch
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.PickedMatchHeaderView
import mg.moneytech.privatecard.navigation.component.SeatInputView
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectorPickPage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            confirmValueChange = { true },
            skipHiddenState = true
        )
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetContent = {
            AnimatedContent(state.buyPage) {
                when (it) {
                    BuyPage.Categorie -> {
                        CategoriePickView(
                            modifier = Modifier.fillMaxWidth(),
                            categories = state.categories,
                            onChooseCategorie = viewModel::chooseCategorie
                        )
                    }

                    BuyPage.Count -> {
                        SeatInputView(
                            modifier = Modifier.fillMaxWidth(),
                            categorie = state.categories[state.selectedCategorie],
                            seatCount = state.seatInput,
                            ready = state.ready,
                            onSeatCountChange = viewModel::updateSeatInput,
                            onIncrementSeatCount = viewModel::incrementSeatInput,
                            onDecrementSeatCount = viewModel::decrementSeatInput,
                            onConfirm = viewModel::confirm,
                            onBack = viewModel::back
                        )
                    }
                }
            }
        },
        sheetDragHandle = null
    ) { paddingValues ->
        PickPage(
            modifier = Modifier.fillMaxSize(),
            match = state.matchs[state.selectedMatch],
            onBack = onBack
        )
    }

}

@Composable
fun PickPage(modifier: Modifier = Modifier, match: Match, onBack: () -> Unit) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(PCIcons.stadium),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            PickedMatchHeaderView(match = match, onBack = onBack)
        }
    }
}


@Composable
private fun SectorPickPageImpl(modifier: Modifier = Modifier) {
    val painter = painterResource(id = PCIcons.stadium)
    val zoomState = rememberZoomState(contentSize = painter.intrinsicSize)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .zoomable(zoomState),
        contentScale = ContentScale.FillHeight
    )
}


@DevicePreviews
@Composable
private fun PickPagePreview() {
    AppTheme {
        PickPage(modifier = Modifier.fillMaxSize(), match = DemoMatch.matchs[0], onBack = {})
    }
}