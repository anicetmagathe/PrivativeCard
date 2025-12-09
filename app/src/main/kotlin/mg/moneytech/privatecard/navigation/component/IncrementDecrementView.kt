package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews

@Composable
fun IncrementDecrementView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    trailingValue: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            CommandButton(
                modifier = Modifier
                    .height(56.dp)
                    .weight(0.3f),
                imageVector = PCIcons.remove,
                shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                onClick = onDecrement,
                containerColor = containerColor,
                contentColor = contentColor
            )

            Row(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .border(width = 1.dp, color = containerColor)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                /*Text(
                    text = "/ $trailingValue",
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                )*/
            }

            CommandButton(
                modifier = Modifier
                    .height(56.dp)
                    .weight(0.3f),
                imageVector = PCIcons.add,
                shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                onClick = onIncrement,
                containerColor = containerColor,
                contentColor = contentColor
            )
        }
    }
}

@Composable
private fun CommandButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Box(modifier = modifier) {
        IconButton(
            onClick = onClick,
            shape = shape,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        }
    }

}

@DevicePreviews
@Composable
private fun IncrementDecrementViewPreview() {
    AppTheme {
        IncrementDecrementView(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            onIncrement = {},
            onDecrement = {},
            trailingValue = "12"
        )
    }
}