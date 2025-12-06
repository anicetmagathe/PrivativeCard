package mg.anet.dll.device.printer

interface Printer {
    suspend fun print(datas: List<PrinterData>): Result<PrintResult>
}