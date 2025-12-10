package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import core.common.format
import core.common.upperCaseFirst
import core.data.demo.DemoMatch
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForClub

@Composable
fun PickedMatchHeaderView(modifier: Modifier = Modifier, match: Match, onBack: () -> Unit) {
    Card(
        modifier = modifier.alpha(0.95f), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = match.date.format("EEEE dd MMM")
                            .upperCaseFirst() + match.date.format(" à kk:mm"),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                    )

                }

                Card(
                    onClick = {},
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray.copy(alpha = 0.4f),
                        contentColor = Color.Black
                    ),
                    shape = CircleShape
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Icon(imageVector = PCIcons.info, contentDescription = null)
                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LoadableImage(
                        model = match.club1.logoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Start),
                        contentScale = ContentScale.FillHeight
                    )

                    Text(
                        text = match.club1.name,
                        modifier = Modifier.align(Alignment.Start),
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start)
                    )
                }

                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
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



                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LoadableImage(
                        model = match.club2.logoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.End),
                        contentScale = ContentScale.FillHeight
                    )

                    Text(
                        text = match.club2.name,
                        modifier = Modifier.align(Alignment.End),
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End)
                    )
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