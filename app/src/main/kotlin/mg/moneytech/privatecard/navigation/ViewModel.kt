package mg.moneytech.privatecard.navigation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.model.entity.Theme
import core.model.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.moneytech.privatecard.repository.CurrentPageRepository
import mg.moneytech.privatecard.repository.ReceiptRepository
import mg.moneytech.privatecard.repository.ReceiptVisibilityRepository
import javax.inject.Inject

enum class Page {
    Home,
    Pick
}

data class MainState(
    val page: Page = Page.Home,
    val theme: Theme = Theme(),
    val showLatestTicket: Boolean = false,
    val receiptImage: Bitmap? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentPageRepository: CurrentPageRepository,
    private val themeRepository: ThemeRepository,
    private val receiptRepository: ReceiptRepository,
    private val receiptVisibilityRepository: ReceiptVisibilityRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            currentPageRepository.current.collect { page ->
                _state.update { it.copy(page = page) }
            }
        }

        viewModelScope.launch {
            themeRepository.get().collect { theme ->
                _state.update { it.copy(theme = theme) }
            }
        }

        viewModelScope.launch {
            receiptRepository.current.collect { bitmap ->
                _state.update { it.copy(receiptImage = bitmap) }
            }
        }

        viewModelScope.launch {
            receiptVisibilityRepository.visible.collect { visible ->
                _state.update { it.copy(showLatestTicket = visible) }
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

    fun hideReceipt() {
        receiptVisibilityRepository.hide()
    }
}
