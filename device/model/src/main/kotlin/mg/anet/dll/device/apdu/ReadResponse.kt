package mg.anet.dll.device.apdu

sealed class ReadResponse(open val code: Int) {
    data object Success : ReadResponse(0x9000)

    data object EndOfFile : ReadResponse(0x6282)

    data object SelectedFileInvalidated : ReadResponse(0x6283)

    data object WrongP1P2 : ReadResponse(0x6B00)

    data object FileOrNotFound : ReadResponse(0x6A82)

    data object SecurityStatusNotSatisfied : ReadResponse(0x6982)

    data object ConditionsOfUseNotSatisfied : ReadResponse(0x6985)

    class WrongLe(code: Int) : ReadResponse(code) {
        init {
            require((code and 0xFF00) == 0x6C00) {
                "WrongLe must be 0x6CXX, got 0x${code.toString(16)}"
            }
        }

        fun getCorrectLe(): Int {
            return code and 0x00FF
        }

        companion object {
            fun isValid(code: Int): Boolean = (code and 0xFF00) == 0x6C00
        }
    }

    data class UnknownError(override val code: Int) : ReadResponse(code)

    companion object {
        fun from(sw: Int): ReadResponse = when (sw) {
            0x9000 -> Success
            0x6282 -> EndOfFile
            0x6283 -> SelectedFileInvalidated
            0x6B00 -> WrongP1P2
            0x6A82 -> FileOrNotFound
            0x6982 -> SecurityStatusNotSatisfied
            0x6985 -> ConditionsOfUseNotSatisfied
            else -> {
                if (WrongLe.isValid(sw)) {
                    WrongLe(sw)
                } else {
                    UnknownError(sw)
                }
            }
        }
    }
}