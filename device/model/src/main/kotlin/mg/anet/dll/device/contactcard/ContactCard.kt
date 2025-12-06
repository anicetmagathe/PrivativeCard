package mg.anet.dll.device.contactcard

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import mg.anet.dll.device.CommandAPDU
import mg.anet.dll.device.Logger
import mg.anet.dll.device.OpenCloseable
import mg.anet.dll.device.ResponseAPDU

interface ContactCard : OpenCloseable {
    suspend fun isInserted(): Boolean

    suspend fun waitInsertion(timeout: Long = 10000L): Boolean {
        val isInserted = withTimeoutOrNull(timeout) {
            while (true) {
                if (isInserted()) {
                    return@withTimeoutOrNull true
                }
                delay(50)
            }
        }

        return isInserted != null
    }

    suspend fun readRecord(): Boolean {
        val request = CommandAPDU(0x00, 0xB2.toByte(), 1, 0xC4.toByte())
        Logger.debug(">>> $request")
        val response = send(request)
        Logger.debug("<<< $response")
        return true
    }

    suspend fun unblock(pin: ByteArray): Boolean {
        val request = CommandAPDU(0x00, 0x20.toByte(), 0, 0, pin)
        Logger.debug(">>> $request")
        val response = send(request)
        return true
    }

    suspend fun verify(pin: ByteArray): Boolean {
        val request = CommandAPDU(0x00, 0x20.toByte(), 0, 0, pin)
        Logger.debug(">>> $request")
        val response = send(request)
        return true
    }

    suspend fun externalAuthentification(code: ByteArray): Boolean {
        val request = CommandAPDU(0x00, 0x82.toByte(), 0, 0, code)
        Logger.debug(">>> $request")
        val response = send(request)
        return true
    }

    suspend fun select(folderName: ByteArray) {
        val request = CommandAPDU(0x00, 0xA4.toByte(), 0x04, 0, folderName, 0)
        Logger.debug(">>> $request")
        val response = send(request)
        Logger.debug("<<< $response")

        with(response) {
            Logger.debug(
                "sw: ${
                    "%04X".format(
                        sw
                    )
                }"
            )

            if (sw != 0x9000) {
                throw RuntimeException("Error: $sw")
            }

            Logger.debug("data: ${data.map { it.toInt().toChar() }.joinToString("")}}")
        }

    }

    suspend fun select(folder: Int) {
        val fid = byteArrayOf((folder shr 8).toByte(), folder.toByte())
        val request = CommandAPDU(0x00, 0xA4.toByte(), 0, 0, fid, 0)
        Logger.debug(">>> $request")
        val response = send(request)
        Logger.debug("<<< $response")

        with(response) {
            Logger.debug(
                "sw: ${
                    "%04X".format(
                        sw
                    )
                }"
            )

            if (sw != 0x9000) {
                throw RuntimeException("Error: $sw")
            }

            Logger.debug("data: ${data.map { it.toInt().toChar() }.joinToString("")}}")
        }

    }

    suspend fun send(apdu: CommandAPDU): ResponseAPDU
}

