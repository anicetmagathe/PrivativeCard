package mg.anet.dll.device.printer

import android.graphics.Bitmap

sealed interface PrinterData {
    val style: Style
}

data class Labeled(
    val left: String,
    val right: String,
    override val style: Style = Style(),
) : PrinterData

data class Text(val text: String, override val style: Style = Style()) : PrinterData

data class Image(
    val image: Bitmap,
    override val style: Style = Style(alignment = Style.Alignment.Center),
) : PrinterData

data class Line(
    val count: Int = 1,
    val char: Char = '-',
    override val style: Style = Style(),
) : PrinterData

data class Newline(
    val count: Int = 1,
    override val style: Style = Style(),
) : PrinterData