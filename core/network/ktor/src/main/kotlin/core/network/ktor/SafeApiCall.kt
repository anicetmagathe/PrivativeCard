package core.network.ktor

import core.network.NetworkError
import core.network.NoNetworkError
import core.network.ServerError
import core.network.UnknownError
import core.network.entity.ApiResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import java.nio.channels.UnresolvedAddressException


internal suspend fun <T : ApiResponse> safeApiCall(block: suspend () -> T): Result<T> {
    return try {
        val result = block()
        if (result.errorCode == "0") {
            Result.success(result)
        } else {
            Result.failure(ServerError(result.errorMessage))
        }
    } catch (e: RedirectResponseException) { // 3xx
        Result.failure(ServerError("Redirect error: ${e.response.status}", e))
    } catch (e: ClientRequestException) { // 4xx
        Result.failure(ServerError("Client error: ${e.response.status}", e))
    } catch (e: ServerResponseException) { // 5xx
        Result.failure(ServerError("Server error: ${e.response.status}", e))
    } catch (e: IOException) {
        Result.failure(NetworkError("Network error: ${e.message}", e))
    } catch (e: UnresolvedAddressException) {
        Result.failure(NetworkError("Network error: ${e.message}", e))
    } catch (e: NoNetworkException) {
        Result.failure(NoNetworkError("Network error: ${e.message}", e))
    } catch (e: Exception) {
        Result.failure(UnknownError("Unknown error: ${e.message}", e))
    }
}