package mg.anet.dll.device

import android.content.Context
import com.spectratech.controllers.MiscController
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Controller @Inject internal constructor(
    @param:ApplicationContext
    private val context: Context,
    private val miscDelegate: MiscDelegate,
) {
    val miscController: MiscController =
        MiscController.getControllerInstance(context, miscDelegate).apply {
            MiscController.enableDebugLog(true)
        }

    fun setEventHandler(handler: MiscDelegate.MiscHandler?) {
        miscDelegate.miscHandler = handler
    }

    fun setDeviceInfoHandler(handler: MiscDelegate.DeviceInfoHandler?) {
        miscDelegate.deviceInfoHandler = handler
    }
}