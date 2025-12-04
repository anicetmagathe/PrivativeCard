package core.model.repository

import core.model.entity.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    fun getAll(): Flow<List<Match>>
}