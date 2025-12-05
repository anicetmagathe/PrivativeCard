package mg.moneytech.privatecard.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.moneytech.privatecard.repository.CurrentPageRepository
import javax.inject.Inject

enum class Page {
    Home,
    Pick
}

data class MainState(val page: Page = Page.Home)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentPageRepository: CurrentPageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            currentPageRepository.current.collect { page ->
                _state.update { it.copy(page = page) }
            }
        }
    }

    fun back() {
        when (_state.value.page) {
            Page.Home -> Unit
            Page.Pick -> {
                currentPageRepository.set(Page.Home)
            }
        }
    }
}
