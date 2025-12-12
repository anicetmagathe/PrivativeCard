package core.data.repository

import androidx.core.graphics.toColorInt
import core.data.demo.DemoCategorie
import core.model.entity.Match
import core.model.entity.Theme
import core.model.repository.MatchRepository
import core.model.repository.ThemeRepository
import core.network.NetworkDataSource
import core.network.entity.asExternal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val themeRepository: ThemeRepository
) : MatchRepository {
    override suspend fun get(): Flow<List<Match>> {
        return _matches
    }

    override suspend fun sync(): Result<Unit> {
        return networkDataSource.getMatches("123456").mapCatching { result ->
            themeRepository.set(
                theme = Theme(
                    backgroundColor = result.backgroundColor.toColorInt(),
                    foregroundColor = result.foregroundColor.toColorInt()
                )
            )

            val matches = result.matches.map { it.asExternal() }.map { match ->
                var categorieIndex = 0
                val categories = match.categories.map {
                    val currentCategorieIndex =
                        if (categorieIndex < DemoCategorie.categories.size) {
                            categorieIndex++
                        } else {
                            0
                        }
                    it.copy(id = DemoCategorie.categories[currentCategorieIndex].id)
                }

                match.copy(
                    club1 = match.club1.copy(
                        logoUrl = match.club1.logoUrl
                    ),
                    club2 = match.club2.copy(
                        logoUrl = match.club2.logoUrl
                    ),
                    categories = categories
                )
            }
            _matches.value = matches
        }
    }

    private val _matches = MutableStateFlow<List<Match>>(emptyList())
}