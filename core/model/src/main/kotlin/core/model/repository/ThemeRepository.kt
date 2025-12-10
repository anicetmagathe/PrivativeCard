package core.model.repository

import core.model.entity.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun get(): Flow<Theme>

    suspend fun set(theme: Theme)
}