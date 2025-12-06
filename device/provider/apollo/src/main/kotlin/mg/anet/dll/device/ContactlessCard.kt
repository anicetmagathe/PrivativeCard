package mg.anet.dll.device

import com.spectratech.controllers.MiscController
import mg.anet.dll.device.contactlesscard.Block
import mg.anet.dll.device.contactlesscard.ContactlessCard
import java.util.Hashtable
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactlessCardImpl @Inject constructor(
    private val controller: Controller,
) : ContactlessCard {
    override suspend fun isInserted(): Boolean {
        return true
    }

    override suspend fun read(block: Block): Result<ByteArray> = suspendCoroutine { continuation ->
        controller.setEventHandler(
            MiscDelegate.MiscHandler(
                onError = { p0, p1 ->
                    continuation.resume(Result.failure(RuntimeException("Error : $p1")))
                },
                onNfcDataExchangeStatusReceived = { p0, p1 ->
                    if (p0) {
                        val dataRaw = p1?.get(MiscController.NFC_NDEF_REC)

                        if (dataRaw == null) {
                            continuation.resume(Result.failure(RuntimeException("Error : Erreur lecture")))
                            return@MiscHandler
                        }

                        val truckedData = dataRaw.takeLast(64)
                        val transformed =
                            truckedData.chunked(2).map { it.toInt(16).toByte() }
                                .toByteArray()

                        continuation.resume(Result.success(transformed))
                    } else {
                        continuation.resume(Result.failure(RuntimeException("Error : $p1")))
                    }
                }
            ))

        controller.miscController.nfcDataExchange(Hashtable<String, Any>().apply {
            /*this[MiscController.NFC_MIF_CARDKEY] =
                lastKey?.value ?: return@apply continuation.resume(
                    Result.Error(
                        message = "Authentication non appeler"
                    )
                )*/
            this[MiscController.NFC_MIF_CARDKEY_NUMBER] = "00"
            this[MiscController.NFC_MIF_CARDBLK_NUMBER] = block.ordinal.toHexString()
            /*((4 * blockNumber.sector.ordinal) + blockNumber.block.ordinal).toHexString()*/
        })

        controller.setEventHandler(null)
    }

    override suspend fun write(
        data: ByteArray,
        block: Block,
    ): Result<Unit> = suspendCoroutine { continuation ->
        controller.setEventHandler(
            MiscDelegate.MiscHandler(
                onError = { p0, p1 ->
                    continuation.resume(Result.failure(RuntimeException("Error : $p1")))
                },
                onNfcDataExchangeStatusReceived = { p0, p1 ->
                    if (p0) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(Result.failure(RuntimeException("Error : $p1")))
                    }
                }
            ))

        controller.miscController.nfcDataExchange(Hashtable<String, Any>().apply {
            /*this[MiscController.NFC_MIF_CARDKEY] = lastKey?.value
                ?: continuation.resume(Result.Error(message = "Authentication non appeler"))*/ // FFFFFFFFFFFF F0F0F0F0F0F0
            this[MiscController.NFC_MIF_CARDKEY_NUMBER] = "00"
            this[MiscController.NFC_MIF_CARDBLK_NUMBER] = "4"
            this[MiscController.NFC_WRITE_NDEF] =
                "D101235402656E3030303030303030303030304646303738304243463046304630463046304630"
        })

        controller.setEventHandler(null)
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