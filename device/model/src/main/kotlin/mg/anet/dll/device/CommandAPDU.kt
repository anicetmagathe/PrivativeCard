package mg.anet.dll.device


@Suppress("ArrayInDataClass")
data class CommandAPDU(
    val cla: Byte,
    val ins: Byte,
    val p1: Byte,
    val p2: Byte,
    val data: ByteArray = byteArrayOf(),
    val le: Int? = null,
) {
    val bytes: ByteArray

    init {
        val header = byteArrayOf(cla, ins, p1, p2)

        bytes = when {
            data.isEmpty() && le == null -> {
                // Case 1: Only header
                header
            }

            data.isNotEmpty() && le == null -> {
                // Case 2: Header + Lc + Data
                header + byteArrayOf(data.size.toByte()) + data
            }

            data.isEmpty() && le != null -> {
                // Case 3: Header + Le
                header + byteArrayOf(le.toByte())
            }

            else -> {
                // Case 4: Header + Lc + Data + Le
                header + byteArrayOf(data.size.toByte()) + data + byteArrayOf(le!!.toByte())
            }
        }
    }

    override fun toString(): String {
        return "CommandAPDU(${bytes.joinToString(" ") { "%02X".format(it) }})"
    }
}

@Suppress("ArrayInDataClass")
data class ResponseAPDU(val bytes: ByteArray) {
    val data: ByteArray
    val sw1: Byte
    val sw2: Byte
    val sw: Int

    init {
        require(bytes.size >= 2) { "Response APDU must be at least 2 bytes long" }

        val len = bytes.size
        data = bytes.copyOfRange(0, len - 2)
        sw1 = bytes[len - 2]
        sw2 = bytes[len - 1]
        sw = ((sw1.toInt() and 0xFF) shl 8) or (sw2.toInt() and 0xFF)
    }

    override fun toString(): String {
        val dataStr = if (data.isEmpty()) "" else data.joinToString(" ") { "%02X".format(it) } + " "
        return "ResponseAPDU(${dataStr}%02X %02X)".format(sw1, sw2)
    }
}
