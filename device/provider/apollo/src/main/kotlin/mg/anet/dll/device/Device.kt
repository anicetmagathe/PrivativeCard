package mg.anet.dll.device

import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DeviceRepositoryImpl @Inject constructor(
    private val controller: Controller,
) : DeviceRepository {
    override suspend fun get(): Device {
        val resultConnect = suspendCoroutine { continuation ->
            val handler = MiscDelegate.MiscHandler(
                onError = { p0, p1 ->
                    continuation.resume(Result.failure(RuntimeException(p1)))

                },
                onControllerConnected = {
                    continuation.resume(Result.success(Unit))
                }
            )
            controller.setEventHandler(handler)
            controller.miscController.connectController()
        }

        if (!resultConnect.isSuccess) {
            return device.copy(serialNumber = "")
        }

        suspendCoroutine { continuation ->
            controller.setDeviceInfoHandler(
                MiscDelegate.DeviceInfoHandler(
                    onError = { p0, p1 ->
                        device = device.copy("")
                        continuation.resume(Unit)
                    },
                    onDeviceInfoReceived = { p0 ->
                        device = device.copy(serialNumber = p0?.get("serialNumber") ?: "")
                        continuation.resume(Unit)
                    }
                ))
            controller.miscController.getDeviceInfo()
        }

        controller.miscController.disconnectController()
        controller.setDeviceInfoHandler(null)

        return device
    }

    private var device = Device(provider = DeviceProvider.Apollo)
}