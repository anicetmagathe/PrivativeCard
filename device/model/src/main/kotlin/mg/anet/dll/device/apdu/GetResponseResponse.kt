package mg.anet.dll.device.apdu

sealed class GetResponseResponse(open val code: Int) {
    data object Success : GetResponseResponse(0x9000)

    class MoreDataStillAvailable(code: Int) : GetResponseResponse(code) {
        init {
            require((code and 0xFF00) == 0x6100) {
                "X must be 0x61XX, got 0x${code.toString(16)}"
            }
        }

        fun get(): Int {
            return code and 0x00FF
        }

        companion object {
            fun isValid(code: Int): Boolean = (code and 0xFF00) == 0x6100
        }
    }

    class WrongLength(code: Int) : GetResponseResponse(code) {
        init {
            require((code and 0xFF00) == 0x6C00) {
                "WrongLe must be 0x6C0X, got 0x${code.toString(16)}"
            }
        }

        fun getLength(): Int {
            return code and 0xFF00
        }

        companion object {
            fun isValid(code: Int): Boolean = (code and 0xFF00) == 0x6C00
        }
    }

    data class UnknownError(override val code: Int) : GetResponseResponse(code)

    companion object {
        fun from(sw: Int): GetResponseResponse = when (sw) {
            0x9000 -> Success
            else -> {
                if (MoreDataStillAvailable.isValid(sw)) {
                    MoreDataStillAvailable(sw)
                } else if (WrongLength.isValid(sw)) {
                    WrongLength(sw)
                } else {
                    UnknownError(sw)
                }
            }
        }
    }
}