package mg.moneytech.privatecard.receipt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import core.async.Dispatcher
import core.async.InDispatchers
import core.common.ReceiptBuilder
import core.common.format
import core.common.takeOrEmpty
import core.common.upperCaseFirst
import core.data.demo.DemoCategorie
import core.data.demo.DemoMatch
import core.designsystem.R
import core.designsystem.theme.AppTheme
import core.model.entity.Categorie
import core.model.entity.Match
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mg.moneytech.privatecard.R.string
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
                s = { context.getString(it) },
                size = 1200
            )
        }
}

fun buildTicket(
    context: Context,
    match: Match,
    categorie: Categorie,
    count: Long,
    s: (Int) -> String,
    size: Int = 1200
): Bitmap {
    val ticketDateTime = LocalDateTime.now()

    return ReceiptBuilder(size)
        .setMargin(30, 20)
        .setAlign(Paint.Align.CENTER)
        .setColor(Color.BLACK)
        .setTextSize(80f)
        .addImage(getBitmapFromRawForPrinter(context, R.raw.logo, dpi = 420))
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .setAlign(Paint.Align.CENTER)
        .addText(
            ticketDateTime.format("EEEE dd MMMM yyyy").upperCaseFirst()
        )
        .setAlign(Paint.Align.CENTER)
        .addText(
            ticketDateTime.format("HH':'mm':'ss")
        )
        .setAlign(Paint.Align.LEFT)
        .addText("Terminal", false)
        .setAlign(Paint.Align.RIGHT)
        .addText("123456")
        .addParagraph()
        .addParagraph()
        .setAlign(Paint.Align.CENTER)
        .addText(s(string.game), false)
        .addLine(lineChar = '=')
        .setAlign(Paint.Align.LEFT)
        .addText(
            match.date.format("EEEE dd MMMM").upperCaseFirst(), false
        )
        .setAlign(Paint.Align.RIGHT)
        .addText(
            match.date.format("HH':'mm")
        )
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .addParagraph()
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .addText(s(string.stadium))
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .addText(match.stadium.name)
        .addParagraph()
        .setAlign(Paint.Align.CENTER)
        .addText(match.club1.name)
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .addText("vs")
        .setAlign(Paint.Align.CENTER)
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .addText(match.club2.name)
        .setTypeface(context, "fonts/roboto_regular.ttf")
        .addLine(lineChar = '=')
        .addParagraph()
        .addParagraph()
        .setAlign(Paint.Align.LEFT)
        .addText(s(string.categorie), false)
        .setAlign(Paint.Align.RIGHT)
        .addText(categorie.name)
        .setAlign(Paint.Align.LEFT)
        .addText(s(string.unit_price), false)
        .setAlign(Paint.Align.RIGHT)
        .addText("${categorie.price.format()}${categorie.currency.name.takeOrEmpty()}")
        .setAlign(Paint.Align.LEFT)
        .addText(s(string.count), false)
        .setAlign(Paint.Align.RIGHT)
        .addText("x $count")
        .setAlign(Paint.Align.LEFT)
        .addParagraph()
        .setAlign(Paint.Align.LEFT)
        .setTextSize(120f)
        .addText(s(string.total), false)
        .setTypeface(context, "fonts/roboto_bold.ttf")
        .setAlign(Paint.Align.RIGHT)
        .addText("${(categorie.price * count).format()}${categorie.currency.name.takeOrEmpty()}")
        .addParagraph()
        .setTextSize(80f)
        .addLine(lineChar = '%')
        .build()

}

fun convertToBlackAndWhite(bitmap: Bitmap, threshold: Int = 128): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val pixels = IntArray(width * height)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

    for (i in pixels.indices) {
        val color = pixels[i]
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        val gray = (r * 0.299 + g * 0.587 + b * 0.114).toInt()

        val bw = if (gray > threshold) 255 else 0
        pixels[i] = Color.rgb(bw, bw, bw)
    }

    val result = createBitmap(width, height)
    result.setPixels(pixels, 0, width, 0, 0, width, height)
    return result
}


fun mmToPixels(mm: Float, dpi: Int = 203): Int {
    val inches = mm / 25.4f // Convert mm to inches
    return (inches * dpi).toInt()
}


fun getBitmapFromRawForPrinter(
    context: Context,
    @RawRes rawId: Int,
    widthMm: Float = 35f,
    dpi: Int = 203
): Bitmap {
    val original = context.resources.openRawResource(rawId).use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }!!

    val targetWidthPx = mmToPixels(widthMm, dpi)
    val aspectRatio = original.height.toFloat() / original.width.toFloat()
    val targetHeightPx = (targetWidthPx * aspectRatio).toInt()

    return convertToBlackAndWhite(original).scale(targetWidthPx, targetHeightPx).also {
        if (it != original) original.recycle()
    }
}

@Preview
@Composable
private fun ReceiptPreview(
) {
    AppTheme {
        val s = { id: Int ->
            when (id) {
                string.game -> "Match"
                string.stadium -> "Stade"
                string.unit_price -> "Prix Unitaire"
                string.count -> "Nombre"
                string.total -> "Total"
                string.categorie -> "Catégorie"
                else -> "Label"
            }
        }

        val context = LocalContext.current
        var bitmap: Bitmap? by remember {
            mutableStateOf(
                buildTicket(
                    context = context,
                    match = DemoMatch.matchs[0],
                    categorie = DemoCategorie.categories[0],
                    s = s,
                    count = 12
                )
            )
        }

        bitmap?.let {
            Image(
                painter = BitmapPainter(
                    it.asImageBitmap()
                ),
                contentDescription = null,
            )
        }
    }
}