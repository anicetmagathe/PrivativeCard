package core.model.entity

enum class NetworkType {
    None,
    Wifi,
    Cellular,
    Ethernet,
    Vpn
}

data class NetworkState(val connected: Boolean = false, val type: NetworkType = NetworkType.None)