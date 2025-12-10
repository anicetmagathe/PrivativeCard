package mg.moneytech.privatecard

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                add { chain ->
                    val request = chain.request.newBuilder()
                        .httpHeaders(
                            NetworkHeaders.Builder()
                                .add(
                                    "User-Agent",
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
                                )
                                .build()
                        )
                        .build()
                    chain.withRequest(request).proceed()
                }
            }
            .build()
    }

}