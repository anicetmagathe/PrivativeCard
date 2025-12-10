package core.data.di

import core.data.repository.MatchRepositoryImpl
import core.data.repository.ThemeRepositoryImpl
import core.model.repository.MatchRepository
import core.model.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun match(impl: MatchRepositoryImpl): MatchRepository

    @Binds
    @Singleton
    abstract fun theme(impl: ThemeRepositoryImpl): ThemeRepository
}