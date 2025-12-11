package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.common.format
import core.common.takeOrEmpty
import core.data.demo.DemoCategorie
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.designsystem.theme.LocalAppTheme
import core.model.entity.Categorie
import core.ui.DevicePreviews
import mg.moneytech.privatecard.R

@Composable
fun SeatInputView(
    modifier: Modifier = Modifier,
    categorie: Categorie,
    seatCount: String,
    priceTotal: Double,
    onSeatCountChange: (String) -> Unit,
    onIncrementSeatCount: () -> Unit,
    onDecrementSeatCount: () -> Unit,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val localTheme = LocalAppTheme.current
    val priceStyle = MaterialTheme.typography.titleLarge.copy(
        fontSize = 35.sp,
        color = Color(localTheme.foregroundColor),
        fontWeight = FontWeight.Bold,
    )

    Column(
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = categorie.description,
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = stringResource(R.string.unit_price),
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray)
                        )

                        Text(
                            text = "${categorie.price.format()}${categorie.currency.name.takeOrEmpty()}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                            )
                        )
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.total_price),
                    style = MaterialTheme.typography.titleLarge,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    AnimatedCounter(
                        counter = priceTotal,
                        style = priceStyle
                    )

                    Text(text = categorie.currency.name.takeOrEmpty(), style = priceStyle)
                }
            }

            IncrementDecrementView(
                modifier = Modifier.fillMaxWidth(),
                value = seatCount,
                onValueChange = onSeatCountChange,
                onIncrement = onIncrementSeatCount,
                onDecrement = onDecrementSeatCount,
                trailingValue = "${categorie.available}",
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            )
        }

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onConfirm,
            label = stringResource(R.string.pay),
            containerColor = Color(localTheme.foregroundColor),
            contentColor = Color(localTheme.backgroundColor),
        )
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
            priceTotal = 12000.0,
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
            priceTotal = 12000.0,
            onSeatCountChange = {},
            onIncrementSeatCount = {},
            onDecrementSeatCount = {},
            onConfirm = {},
            onBack = {}
        )
    }
}