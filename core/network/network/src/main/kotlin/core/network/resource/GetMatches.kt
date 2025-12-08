@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)

package core.network.resource

import core.network.entity.ApiResponse
import core.network.entity.Match
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object GetMatches {
    @Serializable
    data object Request

    @Serializable
    data class Response(
        @SerialName("ErrorCode") override val errorCode: String,

        @SerialName("ErrorMessage") override val errorMessage: String,

        @SerialName("Matches") val matches: List<Match>
    ) : ApiResponse
}