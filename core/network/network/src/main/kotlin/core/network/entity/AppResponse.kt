package core.network.entity

interface ApiResponse {
    val errorCode: String

    val errorMessage: String
}