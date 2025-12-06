package mg.anet.dll.device

import com.nexgo.oaf.apiv3.card.cpu.CPUCardHandler
import com.nexgo.oaf.apiv3.device.reader.CardReader
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import mg.anet.dll.device.contactcard.ContactCard
import javax.inject.Inject

class ContactCardImpl @Inject constructor(
    private val cardReader: CardReader,
    private val cpuCardHandler: CPUCardHandler,
) : ContactCard {
    override suspend fun isInserted(): Boolean {
        return true
    }

    override suspend fun send(apdu: CommandAPDU): ResponseAPDU {
        throw NotImplementedError("Not yet implemented")
    }

    override fun close() {
        cardReader.close(CardSlotTypeEnum.ICC1)
    }

    override fun open() {
        cardReader.open(CardSlotTypeEnum.ICC1)
    }
}