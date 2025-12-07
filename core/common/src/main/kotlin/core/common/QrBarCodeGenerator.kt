package core.common

//import com.google.zxing.BarcodeFormat
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix


enum class BarcodeFormat {
    /** Aztec 2D barcode format.  */
    AZTEC,

    /** CODABAR 1D format.  */
    CODABAR,

    /** Code 39 1D format.  */
    CODE_39,

    /** Code 93 1D format.  */
    CODE_93,

    /** Code 128 1D format.  */
    CODE_128,

    /** Data Matrix 2D barcode format.  */
    DATA_MATRIX,

    /** EAN-8 1D format.  */
    EAN_8,

    /** EAN-13 1D format.  */
    EAN_13,

    /** ITF (Interleaved Two of Five) 1D format.  */
    ITF,

    /** MaxiCode 2D barcode format.  */
    MAXICODE,

    /** PDF417 format.  */
    PDF_417,

    /** QR Code 2D barcode format.  */
    QR_CODE,

    /** RSS 14  */
    RSS_14,

    /** RSS EXPANDED  */
    RSS_EXPANDED,

    /** UPC-A 1D format.  */
    UPC_A,

    /** UPC-E 1D format.  */
    UPC_E,

    /** UPC/EAN extension format. Not a stand-alone format.  */
    UPC_EAN_EXTENSION
}

private fun mapBarcodeFormat(format: BarcodeFormat): com.google.zxing.BarcodeFormat {
    return when (format) {
        BarcodeFormat.AZTEC -> com.google.zxing.BarcodeFormat.AZTEC
        BarcodeFormat.CODABAR -> com.google.zxing.BarcodeFormat.CODABAR
        BarcodeFormat.CODE_39 -> com.google.zxing.BarcodeFormat.CODE_39
        BarcodeFormat.CODE_93 -> com.google.zxing.BarcodeFormat.CODE_93
        BarcodeFormat.CODE_128 -> com.google.zxing.BarcodeFormat.CODE_128
        BarcodeFormat.DATA_MATRIX -> com.google.zxing.BarcodeFormat.DATA_MATRIX
        BarcodeFormat.EAN_8 -> com.google.zxing.BarcodeFormat.EAN_8
        BarcodeFormat.EAN_13 -> com.google.zxing.BarcodeFormat.EAN_13
        BarcodeFormat.ITF -> com.google.zxing.BarcodeFormat.ITF
        BarcodeFormat.MAXICODE -> com.google.zxing.BarcodeFormat.MAXICODE
        BarcodeFormat.PDF_417 -> com.google.zxing.BarcodeFormat.PDF_417
        BarcodeFormat.QR_CODE -> com.google.zxing.BarcodeFormat.QR_CODE
        BarcodeFormat.RSS_14 -> com.google.zxing.BarcodeFormat.RSS_14
        BarcodeFormat.RSS_EXPANDED -> com.google.zxing.BarcodeFormat.RSS_EXPANDED
        BarcodeFormat.UPC_A -> com.google.zxing.BarcodeFormat.UPC_A
        BarcodeFormat.UPC_E -> com.google.zxing.BarcodeFormat.UPC_E
        BarcodeFormat.UPC_EAN_EXTENSION -> com.google.zxing.BarcodeFormat.UPC_EAN_EXTENSION
    }
}


fun generateBarcodeBitmap(
    content: String,
    format: BarcodeFormat,
    width: Int,
    height: Int
): Bitmap? {
    try {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            mapBarcodeFormat(format),
            width,
            height
        )

        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (bitMatrix.get(x, y)) {
                    Color.BLACK
                } else {
                    Color.WHITE
                }
            }
        }

        return Bitmap.createBitmap(
            pixels,
            width,
            height,
            Bitmap.Config.ARGB_8888
        )

    } catch (e: WriterException) {
        e.printStackTrace()
        return null
    }
}

fun generateQrCodeBitmap(
    content: String,
    width: Int = 512,
    height: Int = 512
): Bitmap? = generateBarcodeBitmap(
    content = content,
    width = width,
    height = height,
    format = BarcodeFormat.QR_CODE
)