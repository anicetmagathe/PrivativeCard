package core.model.repository

import core.model.entity.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    suspend fun get(): Flow<List<Match>>

    suspend fun sync(): Result<Unit>
}