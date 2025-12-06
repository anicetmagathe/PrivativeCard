package mg.anet.dll.device

import com.pax.dal.IPicc
import com.pax.dal.entity.EDetectMode
import com.pax.dal.entity.EM1KeyType
import mg.anet.dll.device.contactlesscard.Block
import mg.anet.dll.device.contactlesscard.ContactlessCard
import javax.inject.Inject

class ContactlessCardImpl @Inject constructor(
    private val iPicc: IPicc,
) : ContactlessCard {
    override suspend fun isInserted(): Boolean {
        return iPicc.detect(EDetectMode.ONLY_M) != null
    }

    override suspend fun read(block: Block): Result<ByteArray> = runCatching {
        iPicc.m1Read(block.toByte())
    }

    override suspend fun write(
        data: ByteArray,
        block: Block,
    ): Result<Unit> = runCatching {
        iPicc.m1Write(block.toByte(), data)
    }

    override suspend fun authentificate(
        block: Block,
        password: ByteArray,
    ): Result<Unit> = runCatching {
        iPicc.m1Auth(EM1KeyType.TYPE_A, block.toByte(), password, byteArrayOf())
    }

    override fun close() {
        iPicc.close()
    }

    override fun open() {
        iPicc.open()
    }
}