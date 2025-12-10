package core.database.di

import core.database.TemplateDatabase
import core.database.dao.TemplateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesImageDao(
        database: TemplateDatabase
    ): TemplateDao = database.templateDao()
}