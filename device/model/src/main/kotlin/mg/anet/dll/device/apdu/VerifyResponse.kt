package mg.anet.dll.device.apdu

sealed class VerifyResponse(open val code: Int) {
    data object Success : VerifyResponse(0x9000)

    data object AuthenticationMethodBlocked : VerifyResponse(0x6983)

    data object SecurityStatusNotSatisfied : VerifyResponse(0x6982)

    data object ConditionsOfUseNotSatisfied : VerifyResponse(0x6985)

    data object IncorrectDataField : VerifyResponse(0x6A80)

    class VerificationFailed(code: Int) : VerifyResponse(code) {
        init {
            require((code and 0xFFF0) == 0x63C0) {
                "WrongLe must be 0x6C0X, got 0x${code.toString(16)}"
            }
        }

        fun getRetryLeft(): Int {
            return code and 0x000F
        }

        companion object {
            fun isValid(code: Int): Boolean = (code and 0xFFF0) == 0x63C0
        }
    }

    data class UnknownError(override val code: Int) : VerifyResponse(code)

    companion object {
        fun from(sw: Int): VerifyResponse = when (sw) {
            0x9000 -> Success
            0x6983 -> AuthenticationMethodBlocked
            0x6982 -> SecurityStatusNotSatisfied
            0x6985 -> ConditionsOfUseNotSatisfied
            0x6A80 -> IncorrectDataField
            else -> {
                if (VerificationFailed.isValid(sw)) {
                    VerificationFailed(sw)
                } else {
                    UnknownError(sw)
                }
            }
        }
    }
}