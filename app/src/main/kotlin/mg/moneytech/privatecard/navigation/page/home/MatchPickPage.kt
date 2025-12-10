package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.designsystem.theme.LocalAppTheme
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.ErrorDialog
import mg.moneytech.privatecard.navigation.component.PickMatchView

@Composable
fun MatchPickPage(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        homeViewModel.refreshMatch()
    }

    val state by homeViewModel.state.collectAsState()
    MatchPickPageImpl(
        modifier = modifier,
        innerPadding = innerPadding,
        state = state,
        onRefresh = homeViewModel::refreshMatch,
        onChooseMatch = homeViewModel::chooseMatch
    )

    state.error?.let {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            ErrorDialog(message = it, onOk = homeViewModel::hideError)
        }
    }
}

@Composable
private fun MatchPickPageImpl(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    state: HomeState,
    onRefresh: () -> Unit,
    onChooseMatch: (Int) -> Unit
) {
    val localTheme = LocalAppTheme.current

    Column(
        modifier = modifier
            .background(color = Color(localTheme.backgroundColor))
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Prochain match",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )

            PullToRefreshBox(
                isRefreshing = state.loading == Loading.Connecting,
                onRefresh = onRefresh,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                ) {
                    itemsIndexed(state.matchs) { index, match ->
                        PickMatchView(
                            modifier = Modifier.fillMaxWidth(),
                            match = match,
                            onClick = { onChooseMatch(index) })
                    }
                }
            }


        }
    }
}

@DevicePreviews
@Composable
private fun MatchPickPagePreview() {
    AppTheme {
        MatchPickPageImpl(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(
                matchs = DemoMatch.matchs,
            ),
            onRefresh = {},
            onChooseMatch = {}
        )
    }
}