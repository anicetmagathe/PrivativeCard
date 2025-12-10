package mg.anet.dll.device.printer

import android.graphics.Bitmap

interface Printer {
    suspend fun print(bitmap: Bitmap): Result<PrintResult>
}