package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Column(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Text(text = label)
        }
    }

}

@DevicePreviews
@Composable
private fun DefaultButtonPreview() {
    AppTheme {
        DefaultButton(modifier = Modifier.fillMaxWidth(), label = "Confirm", onClick = {})
    }
}