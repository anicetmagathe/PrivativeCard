package core.domain

import javax.inject.Inject

class ValidateSeatCountUseCase @Inject constructor() {
    operator fun invoke(value: String): Result<Unit> {
        if (!regex.matches(value)) {
            return Result.failure(kotlin.Exception("Invalid money"))
        }

        return Result.success(Unit)
    }

    fun mayBeValid(value: String): Result<Unit> {
        if (!partialRegex.matches(value)) {
            return Result.failure(kotlin.Exception("Invalid money"))
        }

        return Result.success(Unit)
    }

    private val regex = Regex("""^\d{1,9}$""")
    private val partialRegex = Regex("""^\d{0,9}$""")
}