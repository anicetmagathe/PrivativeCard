package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.MainClub
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.ErrorDialog
import mg.moneytech.privatecard.navigation.component.PickMatchView
import mg.moneytech.privatecard.navigation.logoForClub

@Composable
fun MatchPickPage(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
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
    Column(
        modifier = modifier.padding(
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
                text = "SÉLECTIONNER UN MATCH",
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

@Composable
private fun ClubViews(modifier: Modifier = Modifier, mainClubs: List<MainClub>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(mainClubs) { mainClub ->
            Card(onClick = {}) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = mainClub.name)

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        mainClub.clubs.chunked(4).forEach { clubParts ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                clubParts.forEach { club ->
                                    Image(
                                        painter = painterResource(logoForClub(club.logo)),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier.size(80.dp)
                                    )
                                }
                            }
                        }
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
                mainClubs = DemoClub.teams,
                matchs = DemoMatch.matchs,
            ),
            onRefresh = {},
            onChooseMatch = {}
        )
    }
}