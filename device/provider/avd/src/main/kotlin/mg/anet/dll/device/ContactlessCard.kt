package mg.anet.dll.device

import mg.anet.dll.device.contactlesscard.Block
import mg.anet.dll.device.contactlesscard.ContactlessCard
import javax.inject.Inject

class ContactlessCardImpl @Inject constructor() : ContactlessCard {
    override suspend fun isInserted(): Boolean {
        return true
    }

    override suspend fun read(block: Block): Result<ByteArray> = runCatching {
        ByteArray(0)
    }

    override suspend fun write(
        data: ByteArray,
        block: Block,
    ): Result<Unit> = runCatching {

    }

    override suspend fun authentificate(
        block: Block,
        password: ByteArray,
    ): Result<Unit> = runCatching {

    }

    override fun close() {

    }

    override fun open() {

    }
}