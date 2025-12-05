package mg.moneytech.privatecard.navigation.page.home

import androidx.lifecycle.ViewModel
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.model.entity.MainClub
import core.model.entity.Match
import core.model.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mg.moneytech.privatecard.navigation.Page
import mg.moneytech.privatecard.repository.CurrentPageRepository
import javax.inject.Inject

data class HomeState(
    val mainClubs: List<MainClub>,
    val matchs: List<Match>,
    val selectedMatch: Int = -1
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val currentPageRepository: CurrentPageRepository
) : ViewModel() {
    private val _state =
        MutableStateFlow(HomeState(mainClubs = DemoClub.teams, matchs = DemoMatch.matchs))
    val state = _state.asStateFlow()

    fun choose(index: Int) {
        _state.update {
            it.copy(selectedMatch = index)
        }
        currentPageRepository.set(Page.Pick)
    }
}