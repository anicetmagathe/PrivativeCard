package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun DashedDivider(
    color: Color = Color.Gray,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(strokeWidth)
) {
    Canvas(modifier = modifier) {
        val strokePx = strokeWidth.toPx()
        val dashPx = dashLength.toPx()
        val gapPx = gapLength.toPx()

        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = strokePx,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashPx, gapPx), 0f
            )
        )
    }
}

@DevicePreviews
@Composable
private fun DashedDividerPreview() {
    AppTheme {
        DashedDivider(modifier = Modifier.fillMaxWidth())
    }
}