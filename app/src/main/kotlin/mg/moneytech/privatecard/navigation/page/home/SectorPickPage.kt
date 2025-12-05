package mg.moneytech.privatecard.navigation.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.data.demo.DemoCategorie
import core.data.demo.DemoMatch
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.model.entity.Match
import core.ui.DevicePreviews
import mg.moneytech.privatecard.navigation.component.PickedMatchHeaderView
import mg.moneytech.privatecard.navigation.component.ZoomableStadiumMap
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectorPickPage(modifier: Modifier = Modifier, match: Match, onBack: () -> Unit) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            confirmValueChange = { false },
            skipHiddenState = true
        )
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            CategoriePickView(
                modifier = Modifier.fillMaxWidth(),
                categories = DemoCategorie.categories
            )
        },
        sheetDragHandle = null
    ) { paddingValues ->
        PickPage(modifier = Modifier.fillMaxSize(), match = match, onBack = onBack)
    }

}

@Composable
fun PickPage(modifier: Modifier = Modifier, match: Match, onBack: () -> Unit) {
    var zoom by remember { mutableFloatStateOf(1f) }

    Box(modifier = modifier) {
        ZoomableStadiumMap(
            modifier = Modifier.size(2000.dp),
            imageResId = PCIcons.stadium,
            zoom = zoom,
            onZoomChange = { zoom = it })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            PickedMatchHeaderView(match = match, onBack = onBack)
        }
    }
}


@Composable
private fun SectorPickPageImpl(modifier: Modifier = Modifier) {
    val painter = painterResource(id = PCIcons.stadium)
    val zoomState = rememberZoomState(contentSize = painter.intrinsicSize)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .zoomable(zoomState),
        contentScale = ContentScale.FillHeight
    )
}


@DevicePreviews
@Composable
private fun PickPagePreview() {
    AppTheme {
        PickPage(modifier = Modifier.fillMaxSize(), match = DemoMatch.matchs[0], onBack = {})
    }
}