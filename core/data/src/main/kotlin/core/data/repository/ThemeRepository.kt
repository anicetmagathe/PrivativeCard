package core.data.repository

import core.model.entity.Theme
import core.model.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor() : ThemeRepository {
    override suspend fun get(): Flow<Theme> {
        return _theme
    }

    override suspend fun set(theme: Theme) {
        _theme.value = theme
    }

    private val _theme = MutableStateFlow(Theme())
}