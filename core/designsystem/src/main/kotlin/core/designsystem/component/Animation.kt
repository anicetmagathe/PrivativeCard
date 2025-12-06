package core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState

@Composable
fun PCAnimation(
    composition: LottieComposition?,
    modifier: Modifier = Modifier,
) {


    val preloaderProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = composition,
        progress = { preloaderProgress },
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}