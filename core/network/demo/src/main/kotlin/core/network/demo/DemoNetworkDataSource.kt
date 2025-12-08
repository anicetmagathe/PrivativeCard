package core.network.demo

import JvmUnitTestAssetManager
import core.async.Dispatcher
import core.async.InDispatchers
import core.network.NetworkDataSource
import core.network.NetworkError
import core.network.ServerError
import core.network.UnknownError
import core.network.entity.ApiResponse
import core.network.resource.GetMatches
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class DemoNetworkDataSource @Inject constructor(
    @param:Dispatcher(InDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val assets: AssetManager = JvmUnitTestAssetManager,
    private val networkJson: Json,
) : NetworkDataSource {
    override suspend fun getMatches(serialNumber: String): Result<GetMatches.Response> =
        getDataFromJsonFile(GET_MATCHES_ASSET)

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T : ApiResponse> getDataFromJsonFile(fileName: String): Result<T> =
        withContext(ioDispatcher) {
            safeFileCall {
                assets.open(fileName).use { inputStream ->
                    networkJson.decodeFromStream(inputStream)
                }
            }
        }


    companion object {
        private const val GET_MATCHES_ASSET = "GetMatches.json"
    }
}

private suspend fun <T : ApiResponse> safeFileCall(block: suspend () -> T): Result<T> {
    return try {
        val result = block()
        if (result.errorCode == "0") {
            Result.success(result)
        } else {
            Result.failure(ServerError(result.errorMessage))
        }
    } catch (e: FileNotFoundException) {
        Result.failure(NetworkError("File not found", e))
    } catch (e: IOException) {
        Result.failure(NetworkError("I/O error while reading file", e))
    } catch (e: SerializationException) {
        Result.failure(ServerError("Failed to parse JSON file", e))
    } catch (e: IllegalArgumentException) {
        Result.failure(ServerError("Invalid data format in JSON file", e))
    } catch (e: Exception) {
        Result.failure(UnknownError("Unknown file read error", e))
    }
}