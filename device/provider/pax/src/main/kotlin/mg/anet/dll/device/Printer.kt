package mg.anet.dll.device

import android.graphics.Bitmap
import com.pax.dal.IDAL
import com.pax.dal.exceptions.PrinterDevException
import mg.anet.dll.device.printer.Image
import mg.anet.dll.device.printer.Labeled
import mg.anet.dll.device.printer.Line
import mg.anet.dll.device.printer.Newline
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.PrinterData
import mg.anet.dll.device.printer.PrinterException
import mg.anet.dll.device.printer.Style
import mg.anet.dll.device.printer.Text
import javax.inject.Inject

class PrinterImpl @Inject constructor(
    idal: IDAL,
) : Printer {
    override suspend fun print(bitmap: Bitmap): Result<PrintResult> = handlePrinterError {
        with(printer) {
            init()
            setGray(3)
            printBitmap(bitmap)
            val result = getError(start())

            if (result == PrintResult.Success) {
                Result.success(result)
            } else {
                Result.failure(PrinterException(message = "$result"))
            }

            Result.success(PrintResult.Success)
        }

    }

    private val printer = idal.printer
}

private suspend fun <T> handlePrinterError(toDo: suspend () -> Result<T>): Result<T> {
    return try {
        toDo()
    } catch (exception: PrinterDevException) {
        Result.failure(PrinterException("Printer error: ${exception.message}"))
    }
}

private fun getError(code: Int): PrintResult {
    return when (code) {
        0 -> PrintResult.Success
        1 -> PrintResult.PrinterIsBusy
        2 -> PrintResult.OutOfPaper
        3 -> PrintResult.TheFormatOfPrintDataPacketError
        4 -> PrintResult.PrinterMalfunctions
        8 -> PrintResult.PrinterOverHeats
        9 -> PrintResult.PrinterVoltageIsTooLow
        -16 -> PrintResult.PrintingIsUnfinished
        -6 -> PrintResult.CutJamError
        -5 -> PrintResult.CoverOpenError
        -4 -> PrintResult.ThePrinterHasNotInstalledFontLibrary
        -2 -> PrintResult.DataPackageIsTooLong
        else -> PrintResult.UnknownError
    }
}

private data class Size(
    val doubleWidth: Boolean,
    val doubleHeight: Boolean,
    val characterCount: Int,
)

private fun getSize(size: Style.Size) = when (size) {
    Style.Size.Small -> Size(doubleWidth = false, doubleHeight = false, characterCount = 32)
    Style.Size.Medium -> Size(doubleWidth = true, doubleHeight = false, characterCount = 16)
    Style.Size.Normal -> Size(doubleWidth = false, doubleHeight = true, characterCount = 32)
    Style.Size.Big -> Size(doubleWidth = true, doubleHeight = true, characterCount = 16)
}

private fun String.padCenter(targetLength: Int, padChar: Char = ' '): String {
    if (this.length >= targetLength) return this
    val totalPadding = targetLength - this.length
    val padLeft = totalPadding / 2
    val padRight = totalPadding - padLeft
    return this
        .padStart(this.length + padLeft, padChar)
        .padEnd(targetLength, padChar)
}