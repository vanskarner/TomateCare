package com.vanskarner.tomatecare.ui.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.vanskarner.tomatecare.R

/**
 * View that allows you to see the bounding boxes
 */
class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBoxModel>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        results = listOf()
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 18f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 18f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 4F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        results.forEach {
            val left = it.x1 * width
            val top = it.y1 * height
            val right = it.x2 * width
            val bottom = it.y2 * height

            canvas.drawRect(left, top, right, bottom, boxPaint)
            val drawableText = it.clsName

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)

        }
    }

    fun setResults(boundingBoxData: List<BoundingBoxModel>) {
        results = boundingBoxData
        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8

        fun cropImageFromBoundingBox(
            imgBitmap: Bitmap,
            boundingBox: BoundingBoxModel
        ): Bitmap {
            val imageWidth = imgBitmap.width
            val imageHeight = imgBitmap.height
            val x1 = boundingBox.x1 * imageWidth
            val y1 = boundingBox.y1 * imageHeight
            val x2 = boundingBox.x2 * imageWidth
            val y2 = boundingBox.y2 * imageHeight
            val croppedImageWidth = (x2 - x1).toInt()
            val croppedImageHeight = (y2 - y1).toInt()
            if (croppedImageWidth <= 0 || croppedImageHeight <= 0)
                throw IllegalArgumentException("The dimensions of the area to be trimmed cannot be <= 0")
            val rectTarget = Rect(0, 0, croppedImageWidth, croppedImageHeight)
            val croppedImage =
                createBitmap(croppedImageWidth, croppedImageHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(croppedImage)
            val rect = Rect(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
            canvas.drawBitmap(imgBitmap, rect, rectTarget, null)
            return croppedImage
        }
    }
}