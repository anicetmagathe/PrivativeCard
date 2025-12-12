package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.common.format
import core.common.takeOrEmpty
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.designsystem.theme.LocalAppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.R
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
    val localTheme = LocalAppTheme.current
    val state by viewModel.state.collectAsState()

    val match by remember(state) { mutableStateOf(state.matchs[state.selectedMatch]) }

    Column(
        modifier = Modifier
            .background(color = Color(localTheme.backgroundColor))
            .padding(bottom = innerPadding.calculateBottomPadding()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding() + 8.dp, start = 8.dp, end = 8.dp)
        ) {
            PickedMatchHeaderView(match = match, onBack = onBack)
        }

        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AnimatedContent(
                targetState = state.buyPage,
            ) {
                when (it) {
                    BuyPage.Categorie -> {
                        CategoriePickView(
                            modifier = Modifier.fillMaxWidth(),
                            categories = match.categories,
                            onChooseCategorie = viewModel::chooseCategorie
                        )
                    }

                    BuyPage.Count -> {
                        SeatInputView(
                            modifier = Modifier.fillMaxWidth(),
                            categorie = match.categories[state.selectedCategorie],
                            seatCount = state.seatInput,
                            priceTotal = state.priceTotal,
                            onSeatCountChange = viewModel::updateSeatInput,
                            onIncrementSeatCount = viewModel::incrementSeatInput,
                            onDecrementSeatCount = viewModel::decrementSeatInput,
                            onConfirm = viewModel::showConfirm,
                            onBack = viewModel::back
                        )
                    }
                }
            }
        }


    }

    if (state.showConfirmation) {
        NoDismissDialog {
            ConfirmationView(
                loading = state.loading,
                match = state.matchs[state.selectedMatch],
                message = buildAnnotatedString {
                    val seatCount = state.seatInput.toInt()
                    append(stringResource(R.string.confirm_buy_of))

                    withStyle(
                        SpanStyle(
                            color = Color(localTheme.foregroundColor),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(" $seatCount ")
                    }

                    append("${pluralStringResource(R.plurals.n_place, seatCount)} ")

                    withStyle(SpanStyle(color = Color(localTheme.foregroundColor))) {
                        append(match.categories[state.selectedCategorie].name)
                    }

                    append(" ${stringResource(R.string.for_price)} ")

                    withStyle(
                        SpanStyle(
                            color = Color(localTheme.foregroundColor),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp
                        )
                    ) {
                        append("${state.priceTotal.format()}${state.matchs[state.selectedMatch].categories[state.selectedCategorie].currency.name.takeOrEmpty()}")
                    }

                    append(" ${stringResource(R.string.question_mark)}")
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
    val localTheme = LocalAppTheme.current
    Box(modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
        Surface(color = Color(localTheme.backgroundColor), modifier = Modifier.fillMaxSize()) { }

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