package core.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.graphics.createBitmap
import androidx.core.graphics.withTranslation


sealed interface ReceiptElement {
    fun measure(width: Int): Int // Returns height needed
    fun draw(canvas: Canvas, y: Float, width: Int, paint: Paint)
}

data class TextElement(
    val text: String,
    val textSize: Float,
    val color: Int,
    val align: Paint.Align,
    val typeface: Typeface?,
    val addNewLine: Boolean
) : ReceiptElement {

    override fun measure(width: Int): Int {
        val paint = Paint().apply {
            this.textSize = this@TextElement.textSize
            this.typeface = this@TextElement.typeface
        }
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        return bounds.height() + if (addNewLine) (textSize * 0.3f).toInt() else 0
    }

    override fun draw(canvas: Canvas, y: Float, width: Int, paint: Paint) {
        paint.apply {
            textSize = this@TextElement.textSize
            color = this@TextElement.color
            textAlign = this@TextElement.align
            typeface = this@TextElement.typeface
        }

        val x = when (align) {
            Paint.Align.CENTER -> width / 2f
            Paint.Align.RIGHT -> width.toFloat()
            else -> 0f
        }

        canvas.drawText(text, x, y + textSize, paint)
    }
}

data class ImageElement(
    val bitmap: Bitmap,
    val align: Paint.Align
) : ReceiptElement {

    override fun measure(width: Int): Int = bitmap.height

    override fun draw(canvas: Canvas, y: Float, width: Int, paint: Paint) {
        val x = when (align) {
            Paint.Align.CENTER -> (width - bitmap.width) / 2f
            Paint.Align.RIGHT -> (width - bitmap.width).toFloat()
            else -> 0f
        }
        canvas.drawBitmap(bitmap, x, y, paint)
    }
}

data class BlankSpaceElement(
    val height: Int
) : ReceiptElement {

    override fun measure(width: Int): Int = height

    override fun draw(canvas: Canvas, y: Float, width: Int, paint: Paint) {
        // Nothing to draw
    }
}

data class LineElement(
    val color: Int,
    val align: Paint.Align,
    val customWidth: Int?,
    val strokeWidth: Float = 2f
) : ReceiptElement {

    override fun measure(width: Int): Int = strokeWidth.toInt() + 8

    override fun draw(canvas: Canvas, y: Float, width: Int, paint: Paint) {
        val lineWidth = customWidth ?: width
        paint.apply {
            color = this@LineElement.color
            strokeWidth = this@LineElement.strokeWidth
            style = Paint.Style.STROKE
        }

        val startX = when (align) {
            Paint.Align.CENTER -> (width - lineWidth) / 2f
            Paint.Align.RIGHT -> (width - lineWidth).toFloat()
            else -> 0f
        }

        canvas.drawLine(startX, y + 4f, startX + lineWidth, y + 4f, paint)
    }
}

// Builder with clean separation of concerns
class ReceiptBuilder(private val width: Int) {
    private val elements = mutableListOf<ReceiptElement>()

    // Current styling state
    private var backgroundColor: Int = Color.WHITE
    private var defaultTextSize: Float = 14f
    private var defaultColor: Int = Color.BLACK
    private var defaultAlign: Paint.Align = Paint.Align.LEFT
    private var defaultTypeface: Typeface? = null
    private var marginTop: Int = 0
    private var marginBottom: Int = 0
    private var marginLeft: Int = 0
    private var marginRight: Int = 0

    fun setTextSize(textSize: Float) = apply {
        this.defaultTextSize = textSize
    }

    fun setBackgroundColor(color: Int) = apply {
        this.backgroundColor = color
    }

    fun setColor(color: Int) = apply {
        this.defaultColor = color
    }

    fun setTypeface(context: Context, typefacePath: String) = apply {
        this.defaultTypeface = Typeface.createFromAsset(context.assets, typefacePath)
    }

    fun setTypeface(typeface: Typeface?) = apply {
        this.defaultTypeface = typeface
    }

    fun setDefaultTypeface() = apply {
        this.defaultTypeface = null
    }

    fun setAlign(align: Paint.Align) = apply {
        this.defaultAlign = align
    }

    fun setMargin(margin: Int) = apply {
        this.marginLeft = margin
        this.marginRight = margin
        this.marginTop = margin
        this.marginBottom = margin
    }

    fun setMargin(topBottom: Int, leftRight: Int) = apply {
        this.marginLeft = leftRight
        this.marginRight = leftRight
        this.marginTop = topBottom
        this.marginBottom = topBottom
    }

    fun setMarginLeft(margin: Int) = apply { this.marginLeft = margin }
    fun setMarginRight(margin: Int) = apply { this.marginRight = margin }
    fun setMarginTop(margin: Int) = apply { this.marginTop = margin }
    fun setMarginBottom(margin: Int) = apply { this.marginBottom = margin }

    fun addText(text: String, newLine: Boolean = true) = apply {
        elements.add(
            TextElement(
                text = text,
                textSize = defaultTextSize,
                color = defaultColor,
                align = defaultAlign,
                typeface = defaultTypeface,
                addNewLine = newLine
            )
        )
    }

    fun addImage(bitmap: Bitmap) = apply {
        elements.add(ImageElement(bitmap, defaultAlign))
    }

    fun addElement(element: ReceiptElement) = apply {
        elements.add(element)
    }

    fun addBlankSpace(height: Int) = apply {
        elements.add(BlankSpaceElement(height))
    }

    fun addParagraph() = apply {
        elements.add(BlankSpaceElement(defaultTextSize.toInt()))
    }

    fun addLine(customWidth: Int? = null) = apply {
        val lineWidth = customWidth ?: (width - marginRight - marginLeft)
        elements.add(LineElement(defaultColor, defaultAlign, lineWidth))
    }

    fun build(): Bitmap {
        val contentWidth = width - marginLeft - marginRight
        val contentHeight = calculateHeight(contentWidth)
        val totalHeight = contentHeight + marginTop + marginBottom

        val bitmap = createBitmap(width, totalHeight)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw background
        canvas.drawColor(backgroundColor)

        // Draw elements
        var currentY = marginTop.toFloat()
        elements.forEach { element ->
            canvas.withTranslation(marginLeft.toFloat(), 0f) {
                element.draw(this, currentY, contentWidth, paint)
            }
            currentY += element.measure(contentWidth)
        }

        return bitmap
    }

    private fun calculateHeight(contentWidth: Int): Int {
        return elements.sumOf { it.measure(contentWidth) } + 10
    }
}