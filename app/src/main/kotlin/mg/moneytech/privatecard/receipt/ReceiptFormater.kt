package mg.moneytech.privatecard.receipt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import core.async.Dispatcher
import core.async.InDispatchers
import core.common.BarcodeFormat
import core.common.ReceiptBuilder
import core.common.format
import core.common.generateBarcodeBitmap
import core.data.demo.DemoCategorie
import core.data.demo.DemoMatch
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.model.entity.Match
import core.ui.DevicePreviews
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class ReceiptFormater @Inject constructor(
    @param:ApplicationContext private val context: Context,
    @param:Dispatcher(InDispatchers.Default) private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun generateSale(
        match: Match,
        categorie: Categorie,
        count: Long
    ): Bitmap =
        withContext(ioDispatcher) {
            buildTicket(
                context = context,
                match = match,
                categorie = categorie,
                count = count,
                size = 1200
            )
        }
}

private fun buildTicket(
    context: Context,
    match: Match,
    categorie: Categorie,
    count: Long,
    size: Int = 1200
): Bitmap {
    val barcodeText = "123456789012"
    val barcode = generateBarcodeBitmap(
        content = barcodeText,
        format = BarcodeFormat.CODE_128,
        width = size,
        height = 300
    )!!

    return ReceiptBuilder(size)
        .setMargin(30, 20)
        .setAlign(Paint.Align.CENTER)
        .setColor(Color.BLACK)
        .setTextSize(80f)
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .addText("LakeFront Cafe")
        .addText("1234 Main St.")
        .addText("Palo Alto, CA 94568")
        .addText("999-999-9999")
        .addBlankSpace(30)
        .setAlign(Paint.Align.LEFT)
        .addText("Terminal: 123456", false)
        .setAlign(Paint.Align.RIGHT)
        .addText("1234")
        .setAlign(Paint.Align.LEFT)
        .addText(LocalDateTime.now().format("dd/MM/yyyy HH:mm"), false)
        .setAlign(Paint.Align.RIGHT)
        .addText("SERVER #4")
        .setAlign(Paint.Align.LEFT)
        .addParagraph()
        .addParagraph()
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .addText(match.club1.name)
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .addText("vs")
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .addText(match.club2.name)
        .addParagraph()
        .addParagraph()
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .setAlign(Paint.Align.LEFT)
        .addText("DATE", false)
        .setAlign(Paint.Align.RIGHT)
        .addText(match.date.format("dd.MM.yyyy HH:mm"))
        .setAlign(Paint.Align.LEFT)
        .addText("PRIX UNITAIRE", false)
        .setAlign(Paint.Align.RIGHT).addText("${categorie.price.format()} €")
        .setAlign(Paint.Align.LEFT)
        .addText("NOMBRE", false)
        .setAlign(Paint.Align.RIGHT)
        .addText("x $count")
        .setAlign(Paint.Align.LEFT)
        .addLine(lineChar = '=')
        .addParagraph()
        .setAlign(Paint.Align.LEFT)
        .setTextSize(120f)
        .addText("TOTAL", false)
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .setAlign(Paint.Align.RIGHT).addText("${(categorie.price * count).format()} €")
        .addParagraph()
        .setAlign(Paint.Align.CENTER)
        .setBackgroundColor(Color.WHITE)
        .setColor(Color.BLACK)
        .addImage(barcode)
        .setTextSize(120f)
        .addText(barcodeText)
        .build()
}

@DevicePreviews
@Composable
private fun ReceiptPreview() {
    AppTheme {
        val context = LocalContext.current
        var bitmap by remember {
            mutableStateOf(
                buildTicket(
                    context = context,
                    match = DemoMatch.matchs[0],
                    categorie = DemoCategorie.categories[0],
                    count = 12
                )
            )
        }

        Image(
            painter = BitmapPainter(bitmap.asImageBitmap()),
            contentDescription = null,
        )
    }
}