package mg.moneytech.privatecard.navigation.page

import androidx.lifecycle.ViewModel
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.model.entity.MainClub
import core.model.entity.Match
import core.model.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeState(val mainClubs: List<MainClub>, val matchs: List<Match>)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _state =
        MutableStateFlow(HomeState(mainClubs = DemoClub.teams, matchs = DemoMatch.matchs))
    val state = _state.asStateFlow()
}