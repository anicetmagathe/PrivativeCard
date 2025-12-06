package mg.moneytech.privatecard.navigation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import core.common.format
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    modifier: Modifier = Modifier,
    counter: Long,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        counter.format()
            .mapIndexed { index, c -> Digit(c, counter, index) }
            .forEach { digit ->
                AnimatedContent(
                    targetState = digit,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } with slideOutVertically { it }
                        } else {
                            slideInVertically { it } with slideOutVertically { -it }
                        }
                    }
                ) { digit ->
                    Text(
                        "${digit.digitChar}",
                        style = style,
                        textAlign = TextAlign.Center,
                    )
                }
            }
    }

}

private data class Digit(val digitChar: Char, val fullNumber: Long, val place: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Digit -> digitChar == other.digitChar
            else -> super.equals(other)
        }
    }
}

private operator fun Digit.compareTo(other: Digit): Int {
    return fullNumber.compareTo(other.fullNumber)
}

@DevicePreviews
@Composable
private fun AnimatedCounterPreview() {
    AppTheme {
        AnimatedCounter(modifier = Modifier.fillMaxWidth(), counter = 1000)
    }
}