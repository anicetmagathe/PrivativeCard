package mg.anet.dll.device.contactlesscard

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import mg.anet.dll.device.OpenCloseable

interface ContactlessCard : OpenCloseable {
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

    suspend fun read(block: Block): Result<ByteArray>

    suspend fun write(data: ByteArray, block: Block): Result<Unit>

    suspend fun authentificate(block: Block, password: ByteArray): Result<Unit>
}