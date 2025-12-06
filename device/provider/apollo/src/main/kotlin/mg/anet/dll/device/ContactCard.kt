package mg.anet.dll.device

import mg.anet.dll.device.contactcard.ContactCard
import javax.inject.Inject

class ContactCardImpl @Inject constructor() : ContactCard {
    override suspend fun isInserted(): Boolean {
        return true
    }

    override suspend fun send(apdu: CommandAPDU): ResponseAPDU {
        throw NotImplementedError("Not yet implemented")
    }

    override fun open() {

    }

    override fun close() {

    }
}