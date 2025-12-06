package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun ConfirmationView(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                title?.let {
                    Text(text = it)
                }

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 28.sp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Cancel",
                        onClick = onCancel,
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    )

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Confirm",
                        onClick = onConfirm,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ConfirmationViewPreview() {
    AppTheme {
        ConfirmationView(
            modifier = Modifier.fillMaxWidth(),
            title = "Title",
            message = "Confirm message",
            onConfirm = {},
            onCancel = {})
    }
}

@DevicePreviews
@Composable
private fun ConfirmationViewNoTitlePreview() {
    AppTheme {
        ConfirmationView(
            modifier = Modifier.fillMaxWidth(),
            message = "Confirm message",
            onConfirm = {},
            onCancel = {})
    }
}