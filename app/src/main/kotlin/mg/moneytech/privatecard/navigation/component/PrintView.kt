package mg.moneytech.privatecard.navigation.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.data.demo.DemoCategorie
import core.data.demo.DemoMatch
import core.designsystem.R
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews
import kotlinx.coroutines.Dispatchers
import mg.moneytech.privatecard.receipt.ReceiptFormater

@Composable
fun PrintView(modifier: Modifier = Modifier, painter: Painter, onHide: () -> Unit = {}) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }

            DefaultButton(label = "OK", onClick = onHide)
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