package mg.anet.dll.device

import com.spectratech.controllers.BaseCardController
import com.spectratech.controllers.ControllerError
import com.spectratech.controllers.ControllerMessage
import com.spectratech.controllers.MiscController
import com.spectratech.controllers.MiscController.MiscDelegate
import java.util.Hashtable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MiscDelegate @Inject internal constructor() :
    MiscDelegate {
    data class MiscHandler(
        val onError: (p0: ControllerError.Error?, p1: String?) -> Unit,
        val onControllerConnected: (() -> Unit)? = null,
        val onControllerDisconnected: (() -> Unit)? = null,
        val onMessageReceived: ((p0: ControllerMessage.MessageText?) -> Unit)? = null,
        val onCardInteractionDetecting: ((p0: BaseCardController.CheckCardMode?) -> Unit)? = null,
        val onDetectCardInteractionAborted: ((p0: Boolean) -> Unit)? = null,
        val onCardInteractionDetected: ((
            p0: BaseCardController.CheckCardResult?,
            p1: Hashtable<String, String>?,
        ) -> Unit)? = null,
        val onCTLAudioToneReceived: ((p0: BaseCardController.ContactlessStatusTone?) -> Unit)? = null,
        val onCTLLightReceived: ((p0: BaseCardController.ContactlessStatusLed?) -> Unit)? = null,
        val onPpSignalOutReceived: ((p0: String?) -> Unit)? = null,
        val onPowerOnIccStatusReceived: ((p0: Boolean, p1: String?, p2: String?, p3: Int) -> Unit)? = null,
        val onPowerOffIccStatusReceived: ((p0: Boolean) -> Unit)? = null,
        val onApduStatusReceived: ((p0: Boolean, p1: Hashtable<String, Any>?) -> Unit)? = null,
        val onNfcDetectCardStatusReceived: ((
            p0: MiscController.NfcDetectCardStatus?,
            p1: Hashtable<String, Any>?,
        ) -> Unit)? = null,
        val onNfcDataExchangeStatusReceived: ((p0: Boolean, p1: Hashtable<String, String>?) -> Unit)? = null,
        val onVasStatusReceived: ((p0: MiscController.VASStatus?, p1: Hashtable<String, Any>?) -> Unit)? = null,
    )

    data class DeviceInfoHandler(
        val onError: (p0: ControllerError.Error?, p1: String?) -> Unit,
        val onDeviceInfoReceived: ((p0: Hashtable<String, String>?) -> Unit)? = null,
    )

    override fun onError(p0: ControllerError.Error?, p1: String?) {
        miscHandler?.onError?.invoke(p0, p1)
        deviceInfoHandler?.onError?.invoke(p0, p1)
    }

    override fun onControllerConnected() {
        miscHandler?.onControllerConnected?.invoke()
    }

    override fun onControllerDisconnected() {
        miscHandler?.onControllerDisconnected?.invoke()
    }

    override fun onDeviceInfoReceived(p0: Hashtable<String, String>?) {
        deviceInfoHandler?.onDeviceInfoReceived?.invoke(p0)
    }

    override fun onMessageReceived(p0: ControllerMessage.MessageText?) {
        miscHandler?.onMessageReceived?.invoke(p0)
    }

    override fun onCardInteractionDetecting(p0: BaseCardController.CheckCardMode?) {
        miscHandler?.onCardInteractionDetecting?.invoke(p0)
    }

    override fun onDetectCardInteractionAborted(p0: Boolean) {
        miscHandler?.onDetectCardInteractionAborted?.invoke(p0)
    }

    override fun onCardInteractionDetected(
        p0: BaseCardController.CheckCardResult?,
        p1: Hashtable<String, String>?,
    ) {
        miscHandler?.onCardInteractionDetected?.invoke(p0, p1)
    }

    override fun onCTLAudioToneReceived(p0: BaseCardController.ContactlessStatusTone?) {
        miscHandler?.onCTLAudioToneReceived?.invoke(p0)
    }

    override fun onCTLLightReceived(p0: BaseCardController.ContactlessStatusLed?) {
        miscHandler?.onCTLLightReceived?.invoke(p0)
    }

    override fun onPpSignalOutReceived(p0: String?) {
        miscHandler?.onPpSignalOutReceived?.invoke(p0)
    }

    override fun onPowerOnIccStatusReceived(p0: Boolean, p1: String?, p2: String?, p3: Int) {
        miscHandler?.onPowerOnIccStatusReceived?.invoke(p0, p1, p2, p3)
    }

    override fun onPowerOffIccStatusReceived(p0: Boolean) {
        miscHandler?.onPowerOffIccStatusReceived?.invoke(p0)
    }

    override fun onApduStatusReceived(p0: Boolean, p1: Hashtable<String, Any>?) {
        miscHandler?.onApduStatusReceived?.invoke(p0, p1)
    }

    override fun onNfcDetectCardStatusReceived(
        p0: MiscController.NfcDetectCardStatus?,
        p1: Hashtable<String, Any>?,
    ) {
        miscHandler?.onNfcDetectCardStatusReceived?.invoke(p0, p1)
    }

    override fun onNfcDataExchangeStatusReceived(p0: Boolean, p1: Hashtable<String, String>?) {
        miscHandler?.onNfcDataExchangeStatusReceived?.invoke(p0, p1)
    }

    override fun onVasStatusReceived(p0: MiscController.VASStatus?, p1: Hashtable<String, Any>?) {
        miscHandler?.onVasStatusReceived?.invoke(p0, p1)
    }

    var miscHandler: MiscHandler? = null
    var deviceInfoHandler: DeviceInfoHandler? = null
}