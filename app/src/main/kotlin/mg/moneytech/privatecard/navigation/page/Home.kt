package mg.moneytech.privatecard.navigation.page

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.MainClub
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.MatchVs
import mg.moneytech.privatecard.navigation.logoForClub

@Composable
fun Home(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel()) {
    val state by homeViewModel.state.collectAsState()
    HomeImpl(modifier = modifier, state = state)
}

@Composable
fun HomeImpl(modifier: Modifier = Modifier, state: HomeState) {
    Column(modifier = modifier) {
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

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(8.dp)
            ) {
                items(state.matchs) { match ->
                    MatchVs(modifier = Modifier.fillMaxWidth(), match = match)
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
private fun HomePreview() {
    AppTheme {
        HomeImpl(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(mainClubs = DemoClub.teams, matchs = DemoMatch.matchs)
        )
    }
}