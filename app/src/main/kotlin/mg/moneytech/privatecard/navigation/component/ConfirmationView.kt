package mg.moneytech.privatecard.navigation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.airbnb.lottie.compose.rememberLottieComposition
import core.data.demo.DemoMatch
import core.designsystem.component.PCAnimation
import core.designsystem.component.PCAnimations
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForClub
import mg.moneytech.privatecard.navigation.page.home.Loading

@Composable
fun ConfirmationView(
    modifier: Modifier = Modifier,
    match: Match,
    title: String? = null,
    loading: Loading = Loading.Ready,
    message: AnnotatedString,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val printingComposition by rememberLottieComposition(PCAnimations.Printing)
    val networkComposition by rememberLottieComposition(PCAnimations.LoadingFootball)
    val successComposition by rememberLottieComposition(PCAnimations.CheckSuccess)
    val errorComposition by rememberLottieComposition(PCAnimations.Error)

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
                AnimatedVisibility(loading != Loading.Printing) {
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
                }

                AnimatedContent(loading) { loading ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        when (loading) {
                            Loading.Ready -> {
                                title?.let {
                                    Text(text = it)
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
                                        containerColor = Color.Yellow.copy(alpha = 0.6f),
                                        contentColor = Color.Black
                                    )

                                    DefaultButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        label = "CONFIRM",
                                        onClick = onConfirm,
                                    )
                                }
                            }

                            Loading.Connecting -> {
                                PCAnimation(
                                    composition = networkComposition,
                                    modifier = Modifier.size(300.dp)
                                )
                            }

                            Loading.Printing -> {
                                PCAnimation(
                                    composition = printingComposition,
                                    modifier = Modifier.size(300.dp)
                                )
                            }

                            Loading.Success -> {
                                PCAnimation(
                                    composition = successComposition,
                                    modifier = Modifier.size(300.dp)
                                )
                            }

                            Loading.Error -> {
                                PCAnimation(
                                    composition = errorComposition,
                                    modifier = Modifier.size(300.dp)
                                )
                            }
                        }
                    }
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

@DevicePreviews
@Composable
private fun ConfirmationViewLoadingPreview() {
    AppTheme {
        ConfirmationView(
            modifier = Modifier.fillMaxWidth(),
            loading = Loading.Connecting,
            match = DemoMatch.matchs[0],
            message = buildAnnotatedString {
                append("Confirm message")
            },
            onConfirm = {},
            onCancel = {})
    }
}