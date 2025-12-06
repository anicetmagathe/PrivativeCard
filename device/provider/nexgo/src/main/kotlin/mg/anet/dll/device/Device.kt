package mg.anet.dll.device

import com.nexgo.oaf.apiv3.DeviceInfo
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceInfo: DeviceInfo,
) : DeviceRepository {
    override suspend fun get(): Device {
        return Device(deviceInfo.sn, DeviceProvider.Nexgo)
    }
}