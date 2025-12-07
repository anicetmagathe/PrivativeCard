package mg.anet.dll.device

import com.nexgo.oaf.apiv3.device.printer.AlignEnum
import com.nexgo.oaf.apiv3.device.printer.GrayLevelEnum
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener
import mg.anet.dll.device.printer.Image
import mg.anet.dll.device.printer.Labeled
import mg.anet.dll.device.printer.Line
import mg.anet.dll.device.printer.Newline
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.PrinterData
import mg.anet.dll.device.printer.Style
import mg.anet.dll.device.printer.Text
import com.nexgo.oaf.apiv3.device.printer.Printer as IPrinter
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PrinterImpl @Inject constructor(
    private val printer: IPrinter
) : Printer {
    override suspend fun print(datas: List<PrinterData>): Result<PrintResult> =
        suspendCoroutine { continuation ->
            with(printer) {
                initPrinter()
                setGray(GrayLevelEnum.LEVEL_4)
                datas.forEach { data ->
                    val alignment = when (data.style.alignment) {
                        Style.Alignment.Left -> AlignEnum.LEFT
                        Style.Alignment.Right -> AlignEnum.RIGHT
                        Style.Alignment.Center -> AlignEnum.CENTER
                    }
                    when (data) {
                        is Image -> appendImage(data.image, alignment)
                        is Labeled -> {}
                        is Line -> {}
                        is Newline -> {}
                        is Text -> {}
                    }
                }

                startPrint(
                    true
                ) { continuation.resume(Result.success(PrintResult.Success)) }
            }
        }
}