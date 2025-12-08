package core.network.demo

import core.model.entity.NetworkState
import core.model.entity.NetworkType
import core.network.NetworkInfo
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DemoNetworkInfo @Inject constructor() : NetworkInfo {
    override val networkState =
        MutableStateFlow(NetworkState(connected = true, type = NetworkType.Wifi))
}