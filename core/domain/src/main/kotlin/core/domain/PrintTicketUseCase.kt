package core.domain

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import core.async.Dispatcher
import core.async.InDispatchers
import core.common.BarcodeFormat
import core.common.ReceiptBuilder
import core.common.format
import core.common.generateBarcodeBitmap
import core.common.generateQrCodeBitmap
import core.model.entity.Categorie
import core.model.entity.Match
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mg.anet.dll.device.printer.Image
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import java.time.LocalDateTime
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
            val barcodeText = "123456789012"
            val barcode = generateBarcodeBitmap(
                content = barcodeText,
                format = BarcodeFormat.CODE_128,
                width = 1200,
                height = 300
            )!!
            val qrcode =
                generateQrCodeBitmap(
                    content = "WIFI:S:MyHomeNet;T:WPA;P:MySecretPass123;;",
                    width = 1200,
                    height = 1200
                )!!


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
                .addText("UNIT PRICE", false)
                .setAlign(Paint.Align.RIGHT).addText("${categorie.price.format()} €")
                .setAlign(Paint.Align.LEFT)
                .addText("COUNT", false)
                .setAlign(Paint.Align.RIGHT)
                .addText("x $count")
                .setAlign(Paint.Align.LEFT)
                .addText("============================================", false)
                .addParagraph()
                .setAlign(Paint.Align.LEFT)
                .setTextSize(150f)
                .addText("TOTAL", false)
                .setTypeface(context, "fonts/roboto_bold.ttf")
                .setAlign(Paint.Align.RIGHT).addText("${(categorie.price * count).format()} €")
                .addParagraph()
                .setAlign(Paint.Align.CENTER)
                .addImage(barcode)
                .setTextSize(120f)
                .addText(barcodeText)
                .build()

            val printerData = buildList {
                add(Image(image = receipt))
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