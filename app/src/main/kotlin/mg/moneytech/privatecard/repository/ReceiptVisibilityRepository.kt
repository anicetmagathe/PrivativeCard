package mg.moneytech.privatecard.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptVisibilityRepository @Inject constructor() {
    private val _visible = MutableStateFlow(false)
    val visible = _visible.asStateFlow()

    fun show() {
        _visible.value = true
    }

    fun hide() {
        _visible.value = false
    }
}