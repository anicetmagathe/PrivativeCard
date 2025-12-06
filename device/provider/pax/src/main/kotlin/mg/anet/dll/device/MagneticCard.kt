package mg.anet.dll.device

import com.pax.dal.IMag
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import mg.anet.dll.device.magneticcard.MagneticCard
import javax.inject.Inject

class MagneticCardImpl @Inject constructor(
    private val iMag: IMag,
) : MagneticCard {
    override suspend fun isSwiped(): Boolean {
        var isSwiped = false

        withTimeoutOrNull(10000) {
            while (true) {
                if (iMag.isSwiped) {
                    isSwiped = true
                    break
                }

                delay(50)
            }
        }

        return isSwiped
    }

    override fun open() {
        iMag.open()
    }

    override fun close() {
        iMag.close()
    }
}