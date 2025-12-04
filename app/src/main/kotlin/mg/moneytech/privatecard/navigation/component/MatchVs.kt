package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.common.format
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.Club
import core.model.entity.Match
import core.model.entity.Stadium
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForClub
import java.time.LocalDateTime

@Composable
fun MatchVs(modifier: Modifier = Modifier, match: Match) {
    Card(
        modifier = modifier,
        onClick = {},
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClubView(modifier = Modifier.weight(0.4F), club = match.club1)

            TimeView(
                modifier = Modifier.weight(0.2F),
                dateTime = match.date,
                stadium = match.stadium
            )

            ClubView(modifier = Modifier.weight(0.4F), club = match.club2, nameFirst = true)
        }
    }
}

@Composable
private fun TimeView(modifier: Modifier = Modifier, dateTime: LocalDateTime, stadium: Stadium) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dateTime.format("HH:mm"),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            text = stadium.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
private fun ClubView(modifier: Modifier = Modifier, club: Club, nameFirst: Boolean = false) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val text = @Composable {
            Text(
                modifier = Modifier.weight(1f),
                text = club.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = if (nameFirst) TextAlign.End else TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                ),
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        val logo = @Composable {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(logoForClub(club.logo)),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }
        if (nameFirst) {
            text()
            logo()

        } else {
            logo()
            text()
        }
    }
}

@DevicePreviews
@Composable
private fun MatchVsPreview() {
    AppTheme {
        MatchVs(
            modifier = Modifier.fillMaxWidth(),
            match = DemoMatch.matchs[0]
        )
    }
}