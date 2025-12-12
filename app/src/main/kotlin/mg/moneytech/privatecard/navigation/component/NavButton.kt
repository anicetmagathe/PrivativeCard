package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun NavButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
    containerColor: Color = Color.LightGray.copy(alpha = 0.7f),
    contentColor: Color = Color.Black
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = CircleShape
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        }
    }

}

@DevicePreviews
@Composable
private fun NavButtonPreview() {
    AppTheme {
        NavButton(imageVector = PCIcons.arrowRight, onClick = {})
    }
}