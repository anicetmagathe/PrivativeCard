package core.common

import android.content.Context
import android.graphics.*
import androidx.core.graphics.createBitmap

// Domain Model - represents what can be drawn
sealed interface ReceiptElement {
    fun getHeight(): Int
    fun drawOnCanvas(canvas: Canvas, x: Float, y: Float)
}

data class TextElement(
    val text: String,
    val textSize: Float,
    val color: Int,
    val align: Paint.Align,
    val typeface: Typeface?,
    val newLine: Boolean,
    val backgroundColor: Int?
) : ReceiptElement {

    override fun getHeight(): Int {
        return textSize.toInt()
    }

    override fun drawOnCanvas(canvas: Canvas, x: Float, y: Float) {
        // Draw background if specified
        if (backgroundColor != null) {
            val paint = Paint().apply {
                color = backgroundColor
                style = Paint.Style.FILL
            }
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                textSize = this@TextElement.textSize
                typeface = this@TextElement.typeface
            }
            val textWidth = textPaint.measureText(text)
            val textHeight = textSize

            // Calculate background rectangle based on alignment
            val left = when (align) {
                Paint.Align.CENTER -> x - textWidth / 2f
                Paint.Align.RIGHT -> x - textWidth
                else -> x
            }
            canvas.drawRect(left, y, left + textWidth, y + textHeight, paint)
        }

        // Draw text
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@TextElement.textSize
            color = this@TextElement.color
            textAlign = this@TextElement.align
            typeface = this@TextElement.typeface
        }
        canvas.drawText(text, x, y + textSize, paint)
    }
}

data class ImageElement(
    val bitmap: Bitmap,
    val align: Paint.Align
) : ReceiptElement {

    override fun getHeight(): Int = bitmap.height

    override fun drawOnCanvas(canvas: Canvas, x: Float, y: Float) {
        canvas.drawBitmap(bitmap, x, y, Paint())
    }
}

data class BlankSpaceElement(
    val spaceHeight: Int
) : ReceiptElement {

    override fun getHeight(): Int = spaceHeight

    override fun drawOnCanvas(canvas: Canvas, x: Float, y: Float) {
        // Nothing to draw
    }
}

data class LineElement(
    val size: Int,
    val color: Int,
    val align: Paint.Align,
    val lineChar: Char?,
    val textSize: Float,
    val typeface: Typeface?
) : ReceiptElement {

    override fun getHeight(): Int = if (lineChar != null) textSize.toInt() else 5

    override fun drawOnCanvas(canvas: Canvas, x: Float, y: Float) {
        if (lineChar != null) {
            // Draw text-based line (e.g., "------" or "======")
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = this@LineElement.color
                textSize = this@LineElement.textSize
                textAlign = Paint.Align.LEFT
                typeface = this@LineElement.typeface
            }
            val charWidth = paint.measureText(lineChar.toString())
            val repeatCount = (size / charWidth).toInt()
            val lineText = lineChar.toString().repeat(repeatCount)
            canvas.drawText(lineText, x, y + textSize, paint)
        } else {
            // Draw solid line
            val paint = Paint().apply {
                color = this@LineElement.color
                strokeWidth = 2f
            }
            canvas.drawLine(x, y, x + size, y, paint)
        }
    }
}

// Builder with clean separation of concerns
class ReceiptBuilder(private val width: Int) {
    private val listItems = mutableListOf<ReceiptElement>()

    // Current styling state
    private var backgroundColor: Int = Color.WHITE
    private var textBackgroundColor: Int? = null
    private var textSize: Float = 14f
    private var color: Int = Color.BLACK
    private var align: Paint.Align = Paint.Align.LEFT
    private var typeface: Typeface? = null
    private var marginTop: Int = 0
    private var marginBottom: Int = 0
    private var marginLeft: Int = 0
    private var marginRight: Int = 0

    fun setTextSize(textSize: Float) = apply {
        this.textSize = textSize
    }

    fun setBackgroundColor(color: Int) = apply {
        this.backgroundColor = color
    }

    fun setTextBackgroundColor(color: Int?) = apply {
        this.textBackgroundColor = color
    }

    fun setColor(color: Int) = apply {
        this.color = color
    }

    fun setTypeface(typeface: Typeface?) = apply {
        this.typeface = typeface
    }

    fun setTypeface(context: Context, assetPath: String) = apply {
        this.typeface = Typeface.createFromAsset(context.assets, assetPath)
    }

    fun setTypeface(context: Context, fontResId: Int) = apply {
        this.typeface = context.resources.getFont(fontResId)
    }

    fun setDefaultTypeface() = apply {
        this.typeface = null
    }

    fun setAlign(align: Paint.Align) = apply {
        this.align = align
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
        listItems.add(
            TextElement(
                text = text,
                textSize = textSize,
                color = color,
                align = align,
                typeface = typeface,
                newLine = newLine,
                backgroundColor = textBackgroundColor
            )
        )
    }

    fun addImage(bitmap: Bitmap) = apply {
        listItems.add(ImageElement(bitmap, align))
    }

    fun addElement(element: ReceiptElement) = apply {
        listItems.add(element)
    }

    fun addBlankSpace(height: Int) = apply {
        listItems.add(BlankSpaceElement(height))
    }

    fun addParagraph() = apply {
        listItems.add(BlankSpaceElement(textSize.toInt()))
    }

    fun addLine(size: Int? = null, lineChar: Char = '-') = apply {
        val lineSize = size ?: (width - marginRight - marginLeft)
        // Use current textSize and typeface settings
        listItems.add(LineElement(lineSize, color, align, lineChar, textSize, typeface))
    }

    fun addSolidLine(size: Int? = null) = apply {
        val lineSize = size ?: (width - marginRight - marginLeft)
        listItems.add(LineElement(lineSize, color, align, null, textSize, typeface))
    }

    private fun getHeight(): Int {
        var height = 5 + marginTop + marginBottom
        for (item in listItems) {
            height += item.getHeight()
        }
        return height
    }

    private fun drawImage(): Bitmap {
        val contentWidth = width - marginRight - marginLeft
        val image = createBitmap(contentWidth, getHeight())
        val canvas = Canvas(image)
        canvas.drawColor(backgroundColor)

        var size = marginTop.toFloat()
        var i = 0
        while (i < listItems.size) {
            val item = listItems[i]

            // Check if this is a text item with newLine = false
            if (item is TextElement && !item.newLine) {
                // Draw this item
                val x = when (item.align) {
                    Paint.Align.CENTER -> contentWidth / 2f
                    Paint.Align.RIGHT -> contentWidth.toFloat()
                    else -> 0f
                }
                item.drawOnCanvas(canvas, x, size)

                // Find next item(s) on the same line
                var j = i + 1
                while (j < listItems.size && listItems[j] is TextElement && !listItems[j-1].let { it is TextElement && it.newLine }) {
                    val nextItem = listItems[j] as TextElement
                    val nextX = when (nextItem.align) {
                        Paint.Align.CENTER -> contentWidth / 2f
                        Paint.Align.RIGHT -> contentWidth.toFloat()
                        else -> 0f
                    }
                    nextItem.drawOnCanvas(canvas, nextX, size)
                    j++
                }

                // Advance size by the text height only once for the whole line
                size += item.textSize
                i = j
                continue
            }

            // Regular item drawing
            val x = when (item) {
                is ImageElement if item.align == Paint.Align.CENTER ->
                    (contentWidth - item.bitmap.width) / 2f

                is ImageElement if item.align == Paint.Align.RIGHT ->
                    (contentWidth - item.bitmap.width).toFloat()

                is LineElement if item.align == Paint.Align.CENTER ->
                    (contentWidth - item.size) / 2f

                is LineElement if item.align == Paint.Align.RIGHT ->
                    (contentWidth - item.size).toFloat()

                is TextElement if item.align == Paint.Align.CENTER ->
                    contentWidth / 2f

                is TextElement if item.align == Paint.Align.RIGHT ->
                    contentWidth.toFloat()

                else -> 0f
            }

            item.drawOnCanvas(canvas, x, size)
            size += item.getHeight()
            i++
        }
        return image
    }

    fun build(): Bitmap {
        val image = createBitmap(width, getHeight())
        val canvas = Canvas(image)
        val paint = Paint()
        canvas.drawColor(backgroundColor)
        canvas.drawBitmap(drawImage(), marginLeft.toFloat(), 0f, paint)
        return image
    }
}