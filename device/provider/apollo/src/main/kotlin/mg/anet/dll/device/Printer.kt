package mg.anet.dll.device

import android.graphics.Bitmap
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.PrinterData
import javax.inject.Inject

class PrinterImpl @Inject constructor() : Printer {
    override suspend fun print(bitmap: Bitmap): Result<PrintResult> {
        return Result.success(PrintResult.Success)
    }
}