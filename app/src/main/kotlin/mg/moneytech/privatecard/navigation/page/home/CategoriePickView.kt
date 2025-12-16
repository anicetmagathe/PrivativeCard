package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.common.format
import core.common.takeOrEmpty
import core.data.demo.DemoCategorie
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.ui.DevicePreviews
import mg.moneytech.privatecard.R
import mg.moneytech.privatecard.navigation.component.CategorieView
import mg.moneytech.privatecard.navigation.component.NavButton

@Composable
fun CategoriePickView(
    modifier: Modifier = Modifier,
    categories: List<Categorie>,
    onChooseCategorie: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(categories) { index, categorie ->
                if (index == 0) {
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))
                }

                CategorieView(
                    modifier = Modifier
                        .fillMaxWidth(),
                    categorie = categorie,
                    onClick = { onChooseCategorie(index) }
                )

                if (index != categories.lastIndex) {
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun CategoriePickViewPreview() {
    AppTheme {
        CategoriePickView(
            modifier = Modifier.fillMaxWidth(),
            categories = DemoCategorie.categories,
            onChooseCategorie = {})
    }
}