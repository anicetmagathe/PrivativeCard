package core.domain

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import com.github.danielfelgar.drawreceiptlib.ReceiptBuilder
import core.async.Dispatcher
import core.async.InDispatchers
import core.common.format
import core.model.entity.Categorie
import core.model.entity.Match
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mg.anet.dll.device.printer.Image
import mg.anet.dll.device.printer.Newline
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.Style
import mg.anet.dll.device.printer.Text
import javax.inject.Inject


class PrintTicketUseCase @Inject constructor(
    private val printer: Printer,
    @param:Dispatcher(InDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    @param:ApplicationContext
    private val context: Context
) {
    suspend operator fun invoke(match: Match, categorie: Categorie, count: Long): Result<Unit> =
        withContext(ioDispatcher) {
            val receipt = ReceiptBuilder(1200)
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
                .addText("Terminal ID: 123456", false)
                .setAlign(Paint.Align.RIGHT)
                .addText("1234")
                .setAlign(Paint.Align.LEFT)
                .addLine()
                .addText("08/15/16", false)
                .setAlign(Paint.Align.RIGHT)
                .addText("SERVER #4")
                .setAlign(Paint.Align.LEFT)
                .addParagraph()
                .addParagraph()
                .setAlign(Paint.Align.CENTER)
                .setTypeface(context, "fonts/roboto_bold.ttf")
                .addText(match.club1.name, true)
                .setAlign(Paint.Align.CENTER)
                .setTypeface(context, "fonts/roboto_regular.ttf")
                .addText("vs", true)
                .setAlign(Paint.Align.CENTER)
                .setTypeface(context, "fonts/roboto_bold.ttf")
                .addText(match.club2.name, true)
                .addParagraph()
                .addParagraph()
                .setTypeface(context, "fonts/roboto_regular.ttf")
                .setAlign(Paint.Align.LEFT)
                .addText("DATE", false)
                .setAlign(Paint.Align.RIGHT)
                .addText(match.date.format("dd.MM.yyyy HH:mm"))
                .setAlign(Paint.Align.LEFT)
                .addText("UNIT PRICE", false)
                .setAlign(Paint.Align.RIGHT).addText("${categorie.price.format()} €")
                .addLine(180)
                .setAlign(Paint.Align.LEFT)
                .addText("COUNT", false)
                .setAlign(Paint.Align.RIGHT)
                .addText("x $count")
                .addParagraph()
                .setAlign(Paint.Align.LEFT)
                .setTypeface(context, "fonts/roboto_bold.ttf")
                .addText("TOTAL", false)
                .setTextSize(100f)
                .setAlign(Paint.Align.RIGHT).addText("${(categorie.price * count).format()} €")
                .setTextSize(80f)
                .addLine(180)
                .addParagraph()
                .setTypeface(context, "fonts/roboto_regular.ttf")
                .setAlign(Paint.Align.CENTER)
                .addText("APPROVED")
                .addParagraph().build()
//                .addImage(barcode)

            val printerData = buildList {
                add(Image(image = receipt))

                add(Newline(5))
            }.asIterable().toList()



            printer.print(printerData).mapCatching {
                if (it == PrintResult.Success) {
                    Unit
                } else {
                    throw Exception("Failed to print ticket")
                }
            }
        }
}