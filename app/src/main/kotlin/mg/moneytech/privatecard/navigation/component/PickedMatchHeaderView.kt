package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.common.format
import core.common.upperCaseFirst
import core.data.demo.DemoMatch
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews

@Composable
fun PickedMatchHeaderView(modifier: Modifier = Modifier, match: Match, onBack: () -> Unit) {
    Card(
        modifier = modifier.alpha(0.95f), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                NavButton(
                    imageVector = PCIcons.arrowRight,
                    onClick = onBack,
                    containerColor = Color.DarkGray.copy(alpha = 0.2f),
                    contentColor = Color.White
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = match.date.format("EEEE dd MMM")
                            .upperCaseFirst() + match.date.format(" à kk:mm"),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                    )

                }

                NavButton(
                    imageVector = PCIcons.info,
                    onClick = onBack,
                    containerColor = Color.DarkGray.copy(alpha = 0.2f),
                    contentColor = Color.White
                )
            }

            ClubVsView(modifier = Modifier.fillMaxWidth(), match = match) {
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Cursive
                            )
                        )
                    }
                }
            }
        }

    }
}

@DevicePreviews
@Composable
private fun PickedMatchHeaderViewPreview() {
    AppTheme {
        PickedMatchHeaderView(
            modifier = Modifier.fillMaxWidth(),
            match = DemoMatch.matchs[0],
            onBack = {})
    }
}