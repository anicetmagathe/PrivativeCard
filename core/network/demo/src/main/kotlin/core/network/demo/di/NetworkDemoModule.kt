package core.network.demo.di

import android.content.Context
import core.network.NetworkDataSource
import core.network.NetworkInfo
import core.network.demo.AssetManager
import core.network.demo.DemoNetworkDataSource
import core.network.demo.DemoNetworkInfo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDemoModule {
    @Binds
    abstract fun networkDataSource(impl: DemoNetworkDataSource): NetworkDataSource

    @Binds
    abstract fun networkInfo(impl: DemoNetworkInfo): NetworkInfo
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkDemoProvidesModule {
    @Provides
    @Singleton
    fun providesAssetManager(
        @ApplicationContext context: Context,
    ): AssetManager = AssetManager(context.assets::open)
}