package mg.anet.dll.device

import android.util.Log

object Logger {
    fun debug(message: String) {
        Log.d("MultiDeviceSDK", message)
    }
}