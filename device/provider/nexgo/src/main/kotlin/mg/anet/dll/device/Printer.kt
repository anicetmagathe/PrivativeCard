package mg.anet.dll.device

import android.graphics.Bitmap
import com.nexgo.oaf.apiv3.device.printer.AlignEnum
import com.nexgo.oaf.apiv3.device.printer.GrayLevelEnum
import mg.anet.dll.device.printer.Image
import mg.anet.dll.device.printer.Labeled
import mg.anet.dll.device.printer.Line
import mg.anet.dll.device.printer.Newline
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.PrinterData
import mg.anet.dll.device.printer.Style
import mg.anet.dll.device.printer.Text
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.nexgo.oaf.apiv3.device.printer.Printer as IPrinter

class PrinterImpl @Inject constructor(
    private val printer: IPrinter
) : Printer {
    override suspend fun print(bitmap: Bitmap): Result<PrintResult> =
        suspendCoroutine { continuation ->
            with(printer) {
                initPrinter()
                setGray(GrayLevelEnum.LEVEL_4)
                appendImage(bitmap, AlignEnum.CENTER)
                startPrint(
                    true
                ) { continuation.resume(Result.success(PrintResult.Success)) }
            }
        }
}