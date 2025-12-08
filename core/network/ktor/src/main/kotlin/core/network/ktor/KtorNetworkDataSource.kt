package core.network.ktor

import core.async.Dispatcher
import core.async.InDispatchers
import core.network.NetworkDataSource
import core.network.NetworkInfo
import core.network.entity.ApiResponse
import core.network.resource.GetMatches
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KtorNetworkDataSource @Inject constructor(
    @param:Dispatcher(InDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val ktorClient: HttpClient,
    private val networkInfo: NetworkInfo,
) : NetworkDataSource {
    override suspend fun getMatches(serialNumber: String): Result<GetMatches.Response> {
        return getJson<GetMatches.Request, GetMatches.Response>(
            resource = "GetMatches/${serialNumber}",
        )
    }

    private suspend inline fun <reified Req : Any, reified Res : ApiResponse> getJson(
        resource: String,
        requestBody: Req? = null,
    ): Result<Res> = withContext(ioDispatcher) {
        safeApiCall {
            if (!networkInfo.networkState.first().connected) {
                throw NoNetworkException(message = "No network connection")
            }

            ktorClient.get("$baseUrl$resource") {
                contentType(ContentType.Application.Json)
                requestBody?.let { setBody(it) }
            }.body<Res>()
        }
    }

    private val baseUrl = "http://www.moneytech.mg:8017/CLUB/ws/Club.svc/1.0/"
}

