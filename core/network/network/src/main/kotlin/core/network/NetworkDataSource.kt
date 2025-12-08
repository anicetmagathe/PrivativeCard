package core.network

import core.network.resource.GetMatches

interface NetworkDataSource {
    suspend fun getMatches(serialNumber: String): Result<GetMatches.Response>
}