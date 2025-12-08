package core.network

sealed class AppError(message: String, cause: Throwable? = null) : Exception(message, cause)

class NoNetworkError(message: String, cause: Throwable? = null) : AppError(message, cause)

class NetworkError(message: String, cause: Throwable? = null) : AppError(message, cause)

class ServerError(message: String, cause: Throwable? = null) : AppError(message, cause)

class UnknownError(message: String, cause: Throwable? = null) : AppError(message, cause)