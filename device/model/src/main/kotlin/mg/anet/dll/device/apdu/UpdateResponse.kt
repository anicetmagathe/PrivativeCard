package mg.anet.dll.device.apdu

sealed class UpdateResponse(open val code: Int) {
    data object Success : UpdateResponse(0x9000)

    data object SelectedFileInvalidated : UpdateResponse(0x6283)

    data object CommandIncompatibleWithFileStructure : UpdateResponse(0x6981)

    data object SecurityStatusNotSatisfied : UpdateResponse(0x6982)

    data object ConditionsOfUseNotSatisfied : UpdateResponse(0x6985)

    data object NotEnoughMemorySpace : UpdateResponse(0x6A84)

    data object FileOrRecordNotFound : UpdateResponse(0x6A82)

    data object WrongP1P2 : UpdateResponse(0x6B00)

    data object INSNotSupported : UpdateResponse(0x6D00)

    data class UnknownError(override val code: Int) : UpdateResponse(code)

    companion object {
        fun from(sw: Int): UpdateResponse = when (sw) {
            0x9000 -> Success
            0x6283 -> SelectedFileInvalidated
            0x6981 -> CommandIncompatibleWithFileStructure
            0x6982 -> SecurityStatusNotSatisfied
            0x6985 -> ConditionsOfUseNotSatisfied
            0x6A84 -> NotEnoughMemorySpace
            0x6A82 -> FileOrRecordNotFound
            0x6B00 -> WrongP1P2
            0x6D00 -> INSNotSupported
            else -> UnknownError(sw)
        }
    }
}