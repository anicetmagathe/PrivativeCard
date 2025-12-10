package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Column(modifier = modifier) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        }
    }

}

@DevicePreviews
@Composable
private fun DefaultIconButtonPreview() {
    AppTheme {
        DefaultIconButton(
            modifier = Modifier.fillMaxWidth(),
            imageVector = PCIcons.add,
            onClick = {})
    }
}