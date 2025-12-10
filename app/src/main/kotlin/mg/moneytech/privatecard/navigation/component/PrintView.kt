package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.designsystem.R
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun PrintView(modifier: Modifier = Modifier, painter: Painter) {
    Column(modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .scrollable(state = rememberScrollState(0), orientation = Orientation.Vertical)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Bitmap Image",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )
            }

            DefaultButton(label = "OK", onClick = {})
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