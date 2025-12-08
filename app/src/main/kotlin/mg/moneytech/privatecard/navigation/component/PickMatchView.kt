package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import core.common.format
import core.common.upperCaseFirst
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.Club
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForClub

@Composable
fun PickMatchView(modifier: Modifier = Modifier, match: Match, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        onClick = onClick,
//        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardDateView(dateTime = match.date)

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = match.date.format("EEEE")
                            .upperCaseFirst() + match.date.format(" à kk:mm"),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(text = buildAnnotatedString {
                        append("${match.description} • ")
                        withStyle(
                            SpanStyle(
                                color = Color.DarkGray,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(match.stadium.name)
                        }
                    })
                }

            }

            DashedDivider(color = Color.LightGray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ClubView(modifier = Modifier, club = match.club1)

                    ClubView(modifier = Modifier, club = match.club2)
                }

                Button(onClick = onClick, shape = RoundedCornerShape(16.dp)) {
                    Text(text = "ACHETER TICKET", style = MaterialTheme.typography.bodyLarge)
                }

            }
        }
    }
}

@Composable
private fun ClubView(modifier: Modifier = Modifier, club: Club) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.size(25.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(logoForClub(club.logo)),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxHeight()
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = club.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            minLines = 1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@DevicePreviews
@Composable
private fun PickMatchViewPreview() {
    AppTheme {
        PickMatchView(
            modifier = Modifier.fillMaxWidth(),
            match = DemoMatch.matchs[0],
            onClick = {}
        )
    }
}