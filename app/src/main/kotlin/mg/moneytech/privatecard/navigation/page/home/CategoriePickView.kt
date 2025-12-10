package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.data.demo.DemoCategorie
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.CategorieView

@Composable
fun CategoriePickView(
    modifier: Modifier = Modifier,
    categories: List<Categorie>,
    onChooseCategorie: (Int) -> Unit
) {
    Column(modifier = modifier) {

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
                        .fillMaxWidth()
                        .clickable { onChooseCategorie(index) },
                    categorie = categorie
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