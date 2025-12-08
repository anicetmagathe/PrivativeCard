package core.network

import core.model.entity.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkInfo {
    val networkState: Flow<NetworkState>
}


