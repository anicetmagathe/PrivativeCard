package mg.anet.dll.device

import android.annotation.SuppressLint
import android.os.Build
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor() : DeviceRepository {
    @SuppressLint("HardwareIds")
    override suspend fun get(): Device {
        return Device(Build.SERIAL, DeviceProvider.Pax)
    }
}