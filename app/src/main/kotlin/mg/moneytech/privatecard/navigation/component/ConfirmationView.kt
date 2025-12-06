package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForClub

@Composable
fun ConfirmationView(
    modifier: Modifier = Modifier,
    match: Match,
    title: String? = null,
    message: AnnotatedString,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                title?.let {
                    Text(text = it)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Image(
                        painter = painterResource(logoForClub(match.club1.logo)),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.size(130.dp)
                    )

                    Image(
                        painter = painterResource(logoForClub(match.club2.logo)),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.size(130.dp)
                    )
                }

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = "CANCEL",
                        onClick = onCancel,
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    )

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = "CONFIRM",
                        onClick = onConfirm,
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun ConfirmationViewPreview() {
    AppTheme {
        ConfirmationView(
            modifier = Modifier.fillMaxWidth(),
            title = "Title",
            match = DemoMatch.matchs[0],
            message = buildAnnotatedString {
                append("Confirm message")
            },
            onConfirm = {},
            onCancel = {})
    }
}

@DevicePreviews
@Composable
private fun ConfirmationViewNoTitlePreview() {
    AppTheme {
        ConfirmationView(
            modifier = Modifier.fillMaxWidth(),
            match = DemoMatch.matchs[0],
            message = buildAnnotatedString {
                append("Confirm message")
            },
            onConfirm = {},
            onCancel = {})
    }
}