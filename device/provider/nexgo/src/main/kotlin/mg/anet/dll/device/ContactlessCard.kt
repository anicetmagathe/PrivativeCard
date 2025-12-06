package mg.anet.dll.device

import com.nexgo.oaf.apiv3.card.mifare.AuthEntity
import com.nexgo.oaf.apiv3.card.mifare.BlockEntity
import com.nexgo.oaf.apiv3.card.mifare.M1CardHandler
import com.nexgo.oaf.apiv3.card.mifare.M1KeyTypeEnum
import com.nexgo.oaf.apiv3.device.reader.CardReader
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import mg.anet.dll.device.contactlesscard.Block
import mg.anet.dll.device.contactlesscard.ContactlessCard
import javax.inject.Inject

class ContactlessCardImpl @Inject constructor(
    private val cardReader: CardReader,
    private val m1CardHandler: M1CardHandler,
) : ContactlessCard {
    override suspend fun isInserted(): Boolean {
        return cardReader.isCardExist(CardSlotTypeEnum.RF)
    }

    override suspend fun read(block: Block): Result<ByteArray> = runCatching {
        val blockEntity = BlockEntity().apply {
            setBlkNo(block.toByte().toInt())
        }

        if (m1CardHandler.readBlock(blockEntity) != 0) {
            throw RuntimeException()
        } else {
            blockEntity.blkData
        }
    }

    override suspend fun write(
        data: ByteArray,
        block: Block,
    ): Result<Unit> = runCatching {
        val blockEntity = BlockEntity().apply {
            setBlkNo(block.toByte().toInt())
            blkData = data
        }

        if (m1CardHandler.writeBlock(blockEntity) != 0) {
            throw RuntimeException()
        } else {
            Unit
        }
    }

    override suspend fun authentificate(
        block: Block,
        password: ByteArray,
    ): Result<Unit> = runCatching {
        val authEntity = AuthEntity().apply {
            setBlkNo(0)
            setPwd(password)
//                setUid(serialNumber)
            setKeyType(M1KeyTypeEnum.KEYTYPE_A)
        }

        if (m1CardHandler.authority(authEntity) != 0) {
            throw RuntimeException()
        } else {
            Unit
        }
    }

    override fun close() {
        cardReader.close(CardSlotTypeEnum.RF)
    }

    override fun open() {
        cardReader.open(CardSlotTypeEnum.RF)
    }
}