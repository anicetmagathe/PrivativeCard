package mg.moneytech.privatecard.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.common.format
import core.data.demo.DemoCategorie
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.logoForCategorie

@Composable
fun CategorieView(modifier: Modifier = Modifier, categorie: Categorie) {
    val logo by remember { mutableIntStateOf(logoForCategorie(categorie.id)) }

    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(80.dp)
                .width(100.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Secteur ${categorie.sector.id}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
            )

            Text(
                text = categorie.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )

            Text(
                text = "€ ${categorie.price.format()}",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Cursive
                ),
                maxLines = 1
            )
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray.copy(alpha = 0.4f),
                contentColor = Color.Black
            ),
            shape = CircleShape
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Icon(imageVector = PCIcons.arrowLeft, contentDescription = null)
            }
        }
    }
}

@DevicePreviews
@Composable
private fun CategorieViewPreview() {
    AppTheme {
        CategorieView(modifier = Modifier.fillMaxWidth(), categorie = DemoCategorie.categories[0])
    }
}