package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.common.format
import core.data.demo.DemoMatch
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.ConfirmationView
import mg.moneytech.privatecard.navigation.component.NoDismissDialog
import mg.moneytech.privatecard.navigation.component.PickedMatchHeaderView
import mg.moneytech.privatecard.navigation.component.SeatInputView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectorPickPage(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
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
        modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetContent = {
            AnimatedContent(
                targetState = state.buyPage,
            ) {
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
                            priceTotal = state.priceTotal,
                            ready = state.ready,
                            onSeatCountChange = viewModel::updateSeatInput,
                            onIncrementSeatCount = viewModel::incrementSeatInput,
                            onDecrementSeatCount = viewModel::decrementSeatInput,
                            onConfirm = viewModel::showConfirm,
                            onBack = viewModel::back
                        )
                    }
                }
            }
        },
        sheetDragHandle = null
    ) { _ ->
        PickPage(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            match = state.matchs[state.selectedMatch],
            onBack = onBack
        )
    }

    if (state.showConfirmation) {
        NoDismissDialog {
            ConfirmationView(
                loading = state.loading,
                match = state.matchs[state.selectedMatch],
                message = buildAnnotatedString {
                    append("Confirm ")

                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("${state.seatInput.toLong()}")
                    }

                    append(" seats of ")

                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(state.categories[state.selectedCategorie].name)
                    }

                    append(" for ")

                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Cursive
                        )
                    ) {
                        append("${state.priceTotal.format()} €")
                    }

                    append(" ?")
                },
                onConfirm = viewModel::confirm,
                onCancel = viewModel::cancel
            )
        }
    }
}

@Composable
fun PickPage(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    match: Match,
    onBack: () -> Unit
) {
    Box(modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
        Image(
            painter = painterResource(PCIcons.stadium),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding() + 8.dp, start = 8.dp, end = 8.dp)
        ) {
            PickedMatchHeaderView(match = match, onBack = onBack)
        }
    }
}

@DevicePreviews
@Composable
private fun PickPagePreview() {
    AppTheme {
        PickPage(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            match = DemoMatch.matchs[0],
            onBack = {})
    }
}