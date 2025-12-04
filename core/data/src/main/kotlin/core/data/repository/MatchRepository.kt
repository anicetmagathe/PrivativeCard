package core.data.repository

import core.data.demo.DemoMatch
import core.model.entity.Match
import core.model.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor() : MatchRepository {
    override fun getAll(): Flow<List<Match>> {
        return flowOf(DemoMatch.matchs)
    }
}