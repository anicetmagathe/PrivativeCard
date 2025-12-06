package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.rememberLottieComposition
import core.designsystem.component.PCAnimation
import core.designsystem.component.PCAnimations
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun ErrorView(modifier: Modifier = Modifier, messsage: String, onClose: () -> Unit) {
    val errorComposition by rememberLottieComposition(PCAnimations.Error)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PCAnimation(
            composition = errorComposition,
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = messsage,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        DefaultButton(
            modifier = Modifier.fillMaxWidth(),
            label = "OK",
            onClick = onClose,
        )
    }
}

@DevicePreviews
@Composable
private fun ErrorViewPreview() {
    AppTheme {
        ErrorView(modifier = Modifier.fillMaxWidth(), messsage = "Error Message", onClose = {})
    }
}