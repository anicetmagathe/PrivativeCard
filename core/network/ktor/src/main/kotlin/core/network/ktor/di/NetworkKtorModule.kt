package core.network.ktor.di

import android.util.Log
import core.network.NetworkDataSource
import core.network.NetworkInfo
import core.network.ktor.KtorNetworkDataSource
import core.network.ktor.KtorNetworkInfo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkKtorModule {
    @Binds
    abstract fun networkDataSource(impl: KtorNetworkDataSource): NetworkDataSource

    @Binds
    abstract fun networkInfo(impl: KtorNetworkInfo): NetworkInfo
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkKtorProvidesModule {
    @Provides
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("PrivateCardLog", message)
                    }
                }

                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }
}