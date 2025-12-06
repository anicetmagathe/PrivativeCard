package mg.anet.dll.device.apdu

sealed class AuthentificateResponse(open val code: Int) {
    data object Success : AuthentificateResponse(0x9000)

    data object SecurityStatusNotSatisfied : AuthentificateResponse(0x6982)

    data object ConditionsOfUseNotSatisfied : AuthentificateResponse(0x6985)

    data object IncorrectParametersInDataField : AuthentificateResponse(0x6A80)

    data class UnknownError(override val code: Int) : AuthentificateResponse(code)

    companion object {
        fun from(sw: Int): AuthentificateResponse = when (sw) {
            0x9000 -> Success
            0x6982 -> SecurityStatusNotSatisfied
            0x6985 -> ConditionsOfUseNotSatisfied
            0x6A80 -> IncorrectParametersInDataField
            else -> UnknownError(sw)
        }
    }
}