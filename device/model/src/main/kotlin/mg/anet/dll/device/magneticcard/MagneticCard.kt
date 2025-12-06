package mg.anet.dll.device.magneticcard

import mg.anet.dll.device.OpenCloseable

interface MagneticCard : OpenCloseable {
    suspend fun isSwiped(): Boolean
}