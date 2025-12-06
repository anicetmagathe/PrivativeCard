package mg.anet.dll.device

import com.pax.dal.IIcc
import com.pax.dal.exceptions.IccDevException
import mg.anet.dll.device.contactcard.ContactCard
import javax.inject.Inject

class ContactCardImpl @Inject constructor(
    private val iIcc: IIcc,
) : ContactCard {
    override suspend fun isInserted(): Boolean {
        return handleError {
            iIcc.detect(0)
        }
    }

    override suspend fun send(apdu: CommandAPDU): ResponseAPDU {
        return handleError {
            val result = iIcc.isoCommand(0, apdu.bytes)

            if (result == null) {
                throw RuntimeException("Response is null")
            }

            ResponseAPDU(result)
        }
    }

    override fun open() {
        handleError {
            iIcc.init(0)
        }
    }

    override fun close() {
        iIcc.close(0)
    }
}

private fun <Resp> handleError(toDo: () -> Resp): Resp {
    return try {
        toDo()
    } catch (e: IccDevException) {
        e.printStackTrace()
        throw RuntimeException("Response is null")
    } catch (e: Exception) {
        e.printStackTrace()
        throw RuntimeException("Response is null")
    }
}