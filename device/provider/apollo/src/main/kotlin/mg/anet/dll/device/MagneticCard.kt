package mg.anet.dll.device

import mg.anet.dll.device.magneticcard.MagneticCard
import javax.inject.Inject

class MagneticCardImpl @Inject constructor() : MagneticCard {
    override suspend fun isSwiped(): Boolean {
        return true
    }

    override fun open() {

    }

    override fun close() {

    }
}