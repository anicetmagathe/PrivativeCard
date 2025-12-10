package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import core.designsystem.R
import core.designsystem.theme.AppTheme
import core.designsystem.theme.LocalAppTheme
import core.ui.DevicePreviews

@Composable
fun PrintView(modifier: Modifier = Modifier, painter: Painter, onHide: () -> Unit = {}) {
    val localTheme = LocalAppTheme.current
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 0.dp)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = buttonSize.height.dp / 2 + 8.dp * 2)
                            .clip(shape = RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    DefaultButton(
                        label = "OK",
                        onClick = onHide,
                        modifier = Modifier
                            .onSizeChanged {
                                buttonSize = it
                            },
                        containerColor = Color(localTheme.foregroundColor),
                        contentColor = Color(localTheme.backgroundColor)
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun PrintViewPreview() {
    AppTheme {
        PrintView(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.stadium_seat_view1)
        )
    }
}