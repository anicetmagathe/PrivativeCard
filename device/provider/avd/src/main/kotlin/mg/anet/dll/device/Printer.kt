package mg.anet.dll.device

import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.PrinterData
import javax.inject.Inject

class PrinterImpl @Inject constructor() : Printer {
    override suspend fun print(datas: List<PrinterData>): Result<PrintResult> {
        return Result.success(PrintResult.Success)
    }
}