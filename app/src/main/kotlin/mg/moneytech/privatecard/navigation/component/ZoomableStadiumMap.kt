package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import coil3.compose.AsyncImage
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews


@Composable
fun ZoomableStadiumMap(
    modifier: Modifier = Modifier,
    imageResId: Int,
    zoom: Float = 1f,
    onZoomChange: (Float) -> Unit
) {
    var offset by remember { mutableStateOf(Offset.Zero) }

    // State to hold the size of the container (the viewport)
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    // Define the boundaries for zoom
    val minScale = 1f
    val maxScale = 4f

    Box(modifier = modifier) {
        AsyncImage(
            model = imageResId,
            contentDescription = "Stadium Seating Map",
            contentScale = ContentScale.Fit, // Keep the image scaled to fit the screen initially
            modifier = Modifier
                .fillMaxSize()
                // Capture the size of the container/viewport
                .onSizeChanged { containerSize = it }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        // --- 1. Calculate and Clamp Scale (Zoom) ---
                        val newScale = (zoom * zoom).coerceIn(minScale, maxScale)
                        onZoomChange(newScale)

                        // --- 2. Calculate and Clamp Offset (Pan) ---
                        // Only allow panning if the image is zoomed in (scale > 1f)
                        if (zoom > minScale) {
                            // Calculate the maximum allowed translation (pan bounds)
                            // The max offset is based on how much of the image is 'off-screen'
                            val maxOffset = IntSize(
                                width = ((containerSize.width * (zoom - 1)) / 2).toInt(),
                                height = ((containerSize.height * (zoom - 1)) / 2).toInt()
                            )

                            // Apply the pan change and clamp it within the boundaries
                            val newOffsetX = (offset.x + pan.x * zoom).coerceIn(
                                -maxOffset.width.toFloat(),
                                maxOffset.width.toFloat()
                            )
                            val newOffsetY = (offset.y + pan.y * zoom).coerceIn(
                                -maxOffset.height.toFloat(),
                                maxOffset.height.toFloat()
                            )
                            offset = Offset(newOffsetX, newOffsetY)
                        } else {
                            // When fully zoomed out, reset the offset to keep the image centered
                            offset = Offset.Zero
                        }
                    }
                }
                .graphicsLayer(
                    // Apply the transformations
                    scaleX = zoom,
                    scaleY = zoom,
                    translationX = offset.x,
                    translationY = offset.y,
                    // Use a pivot point for scaling (usually center)
                    transformOrigin = TransformOrigin.Center
                )
        )
    }
}

@DevicePreviews
@Composable
private fun ZoomableStadiumMapPreview() {
    AppTheme {
        ZoomableStadiumMap(
            modifier = Modifier.fillMaxSize(),
            imageResId = PCIcons.stadium,
            onZoomChange = {})
    }
}