package mg.moneytech.privatecard.navigation.page.pick

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.designsystem.component.PCIcons
import core.designsystem.theme.AppTheme
import core.ui.DevicePreviews
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectorPickPage(modifier: Modifier = Modifier) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        // Set the initial state to Collapsed or PartiallyExpanded
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false // Optionally allow the sheet to be fully hidden
        )
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp, // The height when the sheet is collapsed
        sheetContent = {
            Text(
                "Bottom Sheet Content - Drag me up!",
                modifier = Modifier.padding(32.dp)
            )
        }
    ) { paddingValues ->
        SectorPickPageImpl(modifier = modifier)
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
private fun SectorPickPagePreview() {
    AppTheme {
        SectorPickPageImpl(modifier = Modifier.fillMaxSize())
    }
}