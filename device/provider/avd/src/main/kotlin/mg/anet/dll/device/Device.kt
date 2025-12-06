package mg.anet.dll.device

import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor() : DeviceRepository {
    override suspend fun get(): Device {
        return Device("", DeviceProvider.Avd)
    }
}