package mg.moneytech.privatecard.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mg.moneytech.privatecard.navigation.Page
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPageRepository @Inject constructor() {
    private val _current = MutableStateFlow(Page.Home)
    val current = _current.asStateFlow()

    fun set(page: Page) {
        _current.value = page
    }
}