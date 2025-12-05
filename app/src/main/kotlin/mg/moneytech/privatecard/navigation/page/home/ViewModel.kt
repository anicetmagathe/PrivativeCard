package mg.moneytech.privatecard.navigation.page.home

import androidx.lifecycle.ViewModel
import core.data.demo.DemoCategorie
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.domain.ValidateSeatCountUseCase
import core.model.entity.Categorie
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

enum class BuyPage {
    Categorie,
    Count
}

data class HomeState(
    val mainClubs: List<MainClub>,
    val matchs: List<Match>,
    val selectedMatch: Int = -1,
    val categories: List<Categorie>,
    val selectedCategorie: Int = -1,
    val buyPage: BuyPage = BuyPage.Categorie,
    val seatInput: String = "",
    val ready: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val currentPageRepository: CurrentPageRepository,
    private val validateSeatCount: ValidateSeatCountUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            HomeState(
                mainClubs = DemoClub.teams,
                matchs = DemoMatch.matchs,
                categories = DemoCategorie.categories
            )
        )
    val state = _state.asStateFlow()

    fun updateSeatInput(value: String) {
        validateSeatCount.mayBeValid(value).fold(
            onSuccess = {
                validateSeatCount(value).fold(
                    onSuccess = {
                        val seatInput = value.toInt()
                        val availableSeat =
                            state.value.categories[state.value.selectedCategorie].available
                        val ready = seatInput < availableSeat
                        _state.update {
                            it.copy(
                                seatInput = if (ready) "$seatInput" else it.seatInput,
                                ready = ready
                            )
                        }
                    },
                    onFailure = {
                        _state.update {
                            it.copy(seatInput = value, ready = false)
                        }
                    }
                )
            },
            onFailure = {}
        )

    }

    fun chooseMatch(index: Int) {
        _state.update {
            it.copy(selectedMatch = index, buyPage = BuyPage.Categorie)
        }
        currentPageRepository.set(Page.Pick)
    }

    fun incrementSeatInput() {
        _state.update {
            it.copy(seatInput = if (it.seatInput.isEmpty()) "1" else "${it.seatInput.toInt() + 1}")
        }
    }

    fun decrementSeatInput() {
        _state.update {
            it.copy(
                seatInput = if (it.seatInput.isEmpty()) "0" else "${
                    (it.seatInput.toInt() - 1).coerceAtLeast(
                        1
                    )
                }"
            )
        }
    }

    fun chooseCategorie(index: Int) {
        _state.update {
            it.copy(
                selectedCategorie = index,
                buyPage = BuyPage.Count,
                seatInput = "1",
                ready = true
            )
        }
    }

    fun back() {
        _state.update {
            it.copy(buyPage = BuyPage.Categorie)
        }
    }

    fun confirm() {
        _state.update {
            it.copy(buyPage = BuyPage.Categorie)
        }

        currentPageRepository.set(Page.Home)
    }
}