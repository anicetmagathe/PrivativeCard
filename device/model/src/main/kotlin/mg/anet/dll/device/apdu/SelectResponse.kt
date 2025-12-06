package mg.anet.dll.device.apdu

sealed class SelectResponse(open val code: Int) {
    data object Success : SelectResponse(0x9000)

    data object SelectedFileInvalidated : SelectResponse(0x6283)

    data object FileOrApplicationNotFound : SelectResponse(0x6A82)

    data object IncorrectP1P2Parameters : SelectResponse(0x6A86)

    data object FunctionNotSupported : SelectResponse(0x6A81)

    data object LcInconsistentWithP1P2 : SelectResponse(0x6A87)

    data object ReferencedDataNotFound : SelectResponse(0x6A88)

    data object CLANotSupported : SelectResponse(0x6E00)

    data object INSNotSupported : SelectResponse(0x6D00)


    data class UnknownError(override val code: Int) : SelectResponse(code)

    companion object {
        fun from(sw: Int): SelectResponse = when (sw) {
            0x9000 -> Success
            0x6283 -> SelectedFileInvalidated
            0x6A82 -> FileOrApplicationNotFound
            0x6A86 -> IncorrectP1P2Parameters
            0x6A81 -> FunctionNotSupported
            0x6A87 -> LcInconsistentWithP1P2
            0x6A88 -> ReferencedDataNotFound
            0x6E00 -> CLANotSupported
            0x6D00 -> INSNotSupported
            else -> UnknownError(sw)
        }
    }
}