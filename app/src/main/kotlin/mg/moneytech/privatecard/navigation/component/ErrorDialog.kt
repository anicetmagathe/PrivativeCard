package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    onOk: () -> Unit,
    onRetry: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Red, contentColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            title?.let {
                Text(text = it, style = MaterialTheme.typography.titleMedium)
            }

            Text(text = message, style = MaterialTheme.typography.titleLarge)

            Column(modifier = Modifier.fillMaxWidth()) {
                ErrorButton(modifier = Modifier.fillMaxWidth(), label = "OK", onClick = onOk)

                onRetry?.let {
                    ErrorButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Réessayer",
                        onClick = it
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(text = label, style = MaterialTheme.typography.titleLarge)
    }
}


@DevicePreviews
@Composable
private fun ErrorDialogPreview() {
    AppTheme {
        ErrorDialog(
            modifier = Modifier.fillMaxWidth(),
            title = "Title",
            message = "Message",
            onOk = {},
            onRetry = {})
    }
}

@DevicePreviews
@Composable
private fun ErrorDialognoRetryPreview() {
    AppTheme {
        ErrorDialog(
            modifier = Modifier.fillMaxWidth(),
            title = "Title",
            message = "Message",
            onOk = {})
    }
}

@DevicePreviews
@Composable
private fun ErrorDialognoTitlePreview() {
    AppTheme {
        ErrorDialog(
            modifier = Modifier.fillMaxWidth(),
            message = "Message",
            onOk = {},
            onRetry = {})
    }
}