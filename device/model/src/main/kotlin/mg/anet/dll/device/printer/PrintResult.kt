package mg.anet.dll.device.printer

enum class PrintResult {
    Success,
    PrinterIsBusy,
    OutOfPaper,
    TheFormatOfPrintDataPacketError,
    PrinterMalfunctions,
    PrinterOverHeats,
    PrinterVoltageIsTooLow,
    PrintingIsUnfinished,
    CutJamError,
    CoverOpenError,
    ThePrinterHasNotInstalledFontLibrary,
    DataPackageIsTooLong,
    UnknownError
}