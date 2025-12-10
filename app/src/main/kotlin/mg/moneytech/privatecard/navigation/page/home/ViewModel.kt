package mg.moneytech.privatecard.navigation.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.data.demo.DemoCategorie
import core.data.demo.DemoClub
import core.data.demo.DemoMatch
import core.domain.PrintTicketUseCase
import core.domain.ValidateSeatCountUseCase
import core.model.entity.Categorie
import core.model.entity.MainClub
import core.model.entity.Match
import core.model.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.moneytech.privatecard.navigation.Page
import mg.moneytech.privatecard.repository.CurrentPageRepository
import javax.inject.Inject

enum class BuyPage {
    Categorie,
    Count
}

enum class Loading {
    Ready,
    Connecting,
    Printing,
    Success,
    Error
}

data class Price(val count: Long, val price: Long)

data class HomeState(
    val mainClubs: List<MainClub> = emptyList(),
    val matchs: List<Match> = emptyList(),
    val selectedMatch: Int = -1,
//    val categories: List<Categorie>,
    val selectedCategorie: Int = -1,
    val buyPage: BuyPage = BuyPage.Categorie,
    val seatInput: String = "",
    val priceTotal: Double = 0.0,
    val ready: Boolean = false,
    val showConfirmation: Boolean = false,
    val loading: Loading = Loading.Ready,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository,
    private val currentPageRepository: CurrentPageRepository,
    private val validateSeatCount: ValidateSeatCountUseCase,
    private val printTicketUseCase: PrintTicketUseCase,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            HomeState(
//                mainClubs = DemoClub.teams,
//                matchs = DemoMatch.matchs,
//                categories = DemoCategorie.categories
            )
        )
    val state = _state.asStateFlow()

    val maxSeatCount = 999L


    fun hideError() {
        _state.update {
            it.copy(error = null)
        }
    }

    fun updateSeatInput(value: String) {
        validateSeatCount.mayBeValid(value).fold(
            onSuccess = {
                validateSeatCount(value).fold(
                    onSuccess = {
                        val seatInput = value.toLong()
                        val ready = seatInput <= maxSeatCount
                        val seatCount = if (ready) seatInput else state.value.seatInput.toLong()

                        _state.update {
                            it.copy(
                                seatInput = "$seatCount",
                                ready = ready,
                                priceTotal = getPrice(seatCount)
                            )
                        }
                    },
                    onFailure = {
                        _state.update {
                            it.copy(seatInput = value, ready = false, priceTotal = 0.0)
                        }
                    }
                )
            },
            onFailure = {}
        )

    }

    fun refreshMatch() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = Loading.Connecting)
            }

            matchRepository.sync().fold(
                onSuccess = {
                    _state.update {
                        it.copy(loading = Loading.Ready, matchs = matchRepository.get().first())
                    }
                },
                onFailure = { reason ->
                    _state.update {
                        it.copy(
                            loading = Loading.Ready,
                            error = reason.message ?: "Erreur obtention liste des matchs"
                        )
                    }
                }
            )
        }
    }

    fun chooseMatch(index: Int) {
        _state.update {
            it.copy(
                selectedMatch = index,
                buyPage = BuyPage.Categorie,
            )
        }
        currentPageRepository.set(Page.Pick)
    }

    fun incrementSeatInput() {
        _state.update {
            val currentSeatCount = it.seatInput.toLongOrNull() ?: 0

            val seatCount = if (currentSeatCount < maxSeatCount) {
                currentSeatCount + 1
            } else {
                currentSeatCount
            }

            it.copy(
                seatInput = "$seatCount",
                priceTotal = getPrice(seatCount)
            )
        }
    }

    fun decrementSeatInput() {
        _state.update {
            val seatCount =
                if (it.seatInput.isEmpty()) 0 else (it.seatInput.toLong() - 1).coerceAtLeast(
                    1
                )

            it.copy(
                seatInput = "$seatCount",
                priceTotal = getPrice(seatCount)
            )
        }
    }

    fun chooseCategorie(index: Int) {
        _state.update {
            val seatCount = 1L
            val categorie = it.matchs[it.selectedMatch].categories[index]
            it.copy(
                selectedCategorie = index,
                buyPage = BuyPage.Count,
                seatInput = "$seatCount",
                priceTotal = categorie.price * seatCount,
                ready = true
            )
        }
    }

    fun back() {
        _state.update {
            it.copy(buyPage = BuyPage.Categorie)
        }
    }

    fun showConfirm() {
        _state.update {
            it.copy(showConfirmation = true, ready = false)
        }
    }

    fun cancel() {
        _state.update {
            it.copy(showConfirmation = false, ready = true)
        }
    }

    fun confirm() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = Loading.Printing)
            }

            val match = selectedMatch()
            val categorie = selectedCategorie()
            val count = state.value.seatInput.toLong()


            printTicketUseCase(match, categorie, count).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            buyPage = BuyPage.Categorie,
                            showConfirmation = false,
                            ready = false,
                            loading = Loading.Ready
                        )
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            loading = Loading.Ready
                        )
                    }
                }
            )

            currentPageRepository.set(Page.Home)
        }

    }

    private fun selectedMatch(): Match {
        return state.value.matchs[state.value.selectedMatch]
    }

    private fun selectedCategorie(): Categorie {
        return selectedMatch().categories[state.value.selectedCategorie]
    }

    private fun getPrice(count: Long): Double {
        val match = state.value.matchs[state.value.selectedMatch]
        val categorie = match.categories[state.value.selectedCategorie]
        return categorie.price * count
    }
}