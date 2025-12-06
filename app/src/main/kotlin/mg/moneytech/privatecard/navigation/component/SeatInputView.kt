package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.common.format
import core.data.demo.DemoCategorie
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.ui.DevicePreviews

@Composable
fun SeatInputView(
    modifier: Modifier = Modifier,
    categorie: Categorie,
    seatCount: String,
    ready: Boolean,
    onSeatCountChange: (String) -> Unit,
    onIncrementSeatCount: () -> Unit,
    onDecrementSeatCount: () -> Unit,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val priceStyle = MaterialTheme.typography.titleLarge.copy(
        fontSize = 35.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive
    )

    val priceFinal by remember(
        categorie,
        seatCount
    ) { mutableLongStateOf(categorie.price * (seatCount.toIntOrNull() ?: 0)) }
    Column(
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        onClick = onBack,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray.copy(alpha = 0.7f),
                            contentColor = Color.Black
                        ),
                        shape = CircleShape
                    ) {
                        Box(modifier = Modifier.padding(8.dp)) {
                            Icon(imageVector = PCIcons.arrowRight, contentDescription = null)
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = categorie.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = "Sector ${categorie.sector.id}",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray)
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Price Unit: ",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    )
                )

                Text(
                    text = "€ ${categorie.price.format()}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Cursive
                    )
                )
            }






            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecrementSeatCount,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    )
                ) {
                    Icon(imageVector = PCIcons.remove, contentDescription = null)
                }

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = seatCount,
                    onValueChange = onSeatCountChange,
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                            Text(
                                text = "/ ${categorie.available}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    placeholder = {
                        /*Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Count",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                )
                            )
                        }*/
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                )
                IconButton(
                    onClick = onIncrementSeatCount,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    )
                ) {
                    Icon(imageVector = PCIcons.add, contentDescription = null)
                }
            }
        }



        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$ ", style = priceStyle)

            AnimatedCounter(counter = priceFinal, style = priceStyle)
        }

        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onConfirm,
            enabled = ready,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = PCIcons.check, contentDescription = null)

                Text(text = "BOOK TICKET")
            }

        }
    }
}

@DevicePreviews
@Composable
private fun SeatInputViewPreview() {
    AppTheme {
        SeatInputView(
            modifier = Modifier.fillMaxWidth(),
            categorie = DemoCategorie.categories[0],
            seatCount = "",
            ready = true,
            onSeatCountChange = {},
            onIncrementSeatCount = {},
            onDecrementSeatCount = {},
            onConfirm = {},
            onBack = {}
        )
    }
}

@DevicePreviews
@Composable
private fun SeatInputViewNotReadyPreview() {
    AppTheme {
        SeatInputView(
            modifier = Modifier.fillMaxWidth(),
            categorie = DemoCategorie.categories[0],
            seatCount = "",
            ready = false,
            onSeatCountChange = {},
            onIncrementSeatCount = {},
            onDecrementSeatCount = {},
            onConfirm = {},
            onBack = {}
        )
    }
}