package mg.anet.dll.device

import com.nexgo.oaf.apiv3.device.reader.CardReader
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import mg.anet.dll.device.magneticcard.MagneticCard
import javax.inject.Inject

class MagneticCardImpl @Inject constructor(
    private val cardReader: CardReader,
) : MagneticCard {
    override suspend fun isSwiped(): Boolean {
        return true
    }

    override fun close() {
        cardReader.close(CardSlotTypeEnum.SWIPE)
    }

    override fun open() {
        cardReader.open(CardSlotTypeEnum.SWIPE)
    }
}