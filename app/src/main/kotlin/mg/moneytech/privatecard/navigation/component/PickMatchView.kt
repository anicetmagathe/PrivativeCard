package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.common.format
import core.common.upperCaseFirst
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.designsystem.theme.LocalAppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.R

@Composable
fun PickMatchView(modifier: Modifier = Modifier, match: Match, onClick: () -> Unit) {
    val localTheme = LocalAppTheme.current

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
                        text = match.date.format("EEEE").upperCaseFirst(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal)
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("${match.description} • ")
                            withStyle(
                                SpanStyle(
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append(match.stadium.name)
                            }
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                    )
                }

            }

            ClubVsView(modifier = Modifier.fillMaxWidth(), match = match) {
                Text(
                    modifier = Modifier.weight(0.2f),
                    text = match.date.format("HH:mm"),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }



            Button(
                onClick = onClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(localTheme.foregroundColor),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(R.string.buy_ticket),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
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