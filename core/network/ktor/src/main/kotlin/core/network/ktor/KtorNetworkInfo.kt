package core.network.ktor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import core.model.entity.NetworkState
import core.model.entity.NetworkType
import core.network.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class KtorNetworkInfo @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
): NetworkInfo {
    override val networkState = MutableStateFlow(NetworkState())


    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            networkState.update {
                it.copy(
                    connected = true,
                    type = it.type
                )
            }
        }

        override fun onLost(network: Network) {
            networkState.update {
                it.copy(
                    connected = false,
                    type = it.type
                )
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities,
        ) {
            networkState.update {
                it.copy(
                    connected = it.connected,
                    type = networkCapabilities.asNetworkType()
                )
            }
        }
    }


    init {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}

private fun NetworkCapabilities.asNetworkType(): NetworkType {
    return when {
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.Wifi
        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.Cellular
        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.Ethernet
        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> NetworkType.Vpn
        else -> NetworkType.None
    }
}