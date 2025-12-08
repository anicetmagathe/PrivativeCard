package core.data.repository

import core.model.entity.Match
import core.model.repository.MatchRepository
import core.network.NetworkDataSource
import core.network.entity.asExternal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) : MatchRepository {
    override suspend fun get(): Flow<List<Match>> {
        return _matches
    }

    override suspend fun sync(): Result<Unit> {
        return networkDataSource.getMatches("123456").mapCatching { result ->
            val matches = result.matches.map { it.asExternal() }
            _matches.value = matches
        }
    }

    private val _matches = MutableStateFlow<List<Match>>(emptyList())
}