package mg.moneytech.privatecard.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptRepository @Inject constructor() {
    private val _current = MutableStateFlow<Bitmap?>(null)
    val current = _current.asStateFlow()

    fun set(bitmap: Bitmap) {
        _current.value = bitmap
    }
}