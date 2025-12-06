package mg.anet.dll.device


data class Device(val serialNumber: String = "", val provider: DeviceProvider = DeviceProvider.Avd)

enum class DeviceProvider {
    Apollo,
    Avd,
    Nexgo,
    Pax
}

interface DeviceRepository {
    suspend fun get(): Device
}