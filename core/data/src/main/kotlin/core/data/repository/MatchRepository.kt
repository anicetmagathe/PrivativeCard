package core.data.repository

import core.data.demo.DemoCategorie
import core.data.demo.DemoClub
import core.model.entity.Club
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
            val matches = result.matches.map { it.asExternal() }.map { match ->
                val defaultLogo = DemoClub.teams[0].clubs[0].logo
                val similarClub1 = searchClub(match.club1.name).getOrNull(0)
                val similarClub2 = searchClub(match.club2.name).getOrNull(0)
                var categorieIndex = 0

                match.copy(
                    club1 = match.club1.copy(logo = similarClub1?.logo ?: defaultLogo),
                    club2 = match.club2.copy(logo = similarClub2?.logo ?: defaultLogo),
                    categories = match.categories.map {
                        val currentCategorieIndex =
                            if (categorieIndex < DemoCategorie.categories.size) {
                                categorieIndex++
                            } else {
                                0
                            }
                        it.copy(id = DemoCategorie.categories[currentCategorieIndex].id)
                    }
                )
            }
            _matches.value = matches
        }
    }

    private val _matches = MutableStateFlow<List<Match>>(emptyList())
}

private fun searchClub(query: String): List<Club> {
    if (query.isBlank()) return emptyList()

    val results = mutableListOf<Club>()
    val searchQuery = query.trim().lowercase()

    DemoClub.teams.forEach { mainClub ->
        mainClub.clubs.forEach { club ->
            if (club.name.lowercase().contains(searchQuery)) {
                results.add(
                    club
                )
            }
        }
    }

    return results
}