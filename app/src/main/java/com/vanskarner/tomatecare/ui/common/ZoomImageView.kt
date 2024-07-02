package com.vanskarner.tomatecare.ui.common

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

class ZoomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), View.OnTouchListener {

    private val matrixScale = Matrix()
    private val savedMatrix = Matrix()
    private val startPoint = PointF()
    private val midPoint = PointF()
    private var mode = NONE
    private var oldDist = 1f
    private var scaleDetector: ScaleGestureDetector
    private var gestureDetector: GestureDetector

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
    }

    init {
        scaleType = ScaleType.MATRIX
        setOnTouchListener(this)
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        handleTouch(event)

        // Return true if the event was handled
        return true
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        fitImageToView()
    }

    override fun setImageDrawable(drawable: android.graphics.drawable.Drawable?) {
        super.setImageDrawable(drawable)
        fitImageToView()
    }

    private fun fitImageToView() {
        val drawable = drawable ?: return

        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight

        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()

        val scale: Float
        val dx: Float
        val dy: Float

        if (imageWidth * viewHeight > viewWidth * imageHeight) {
            scale = viewHeight / imageHeight.toFloat()
            dx = (viewWidth - imageWidth * scale) * 0.5f
            dy = 0f
        } else {
            scale = viewWidth / imageWidth.toFloat()
            dx = 0f
            dy = (viewHeight - imageHeight * scale) * 0.5f
        }

        matrixScale.setScale(scale, scale)
        matrixScale.postTranslate(dx, dy)
        imageMatrix = matrixScale
    }

    private fun handleTouch(event: MotionEvent) {
        val scale = FloatArray(9)
        matrixScale.getValues(scale)

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(matrixScale)
                startPoint.set(event.x, event.y)
                mode = DRAG
                performClick()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrixScale)
                    midPoint(midPoint, event)
                    mode = ZOOM
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode == DRAG) {
                    matrixScale.set(savedMatrix)
                    matrixScale.postTranslate(event.x - startPoint.x, event.y - startPoint.y)
                } else if (mode == ZOOM) {
                    val newDist = spacing(event)
                    if (newDist > 10f) {
                        matrixScale.set(savedMatrix)
                        val newScale = newDist / oldDist
                        matrixScale.postScale(newScale, newScale, midPoint.x, midPoint.y)
                    }
                }
            }
        }

        imageMatrix = matrixScale
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            val values = FloatArray(9)
            matrixScale.getValues(values)
            val currentScale = values[Matrix.MSCALE_X]
            val newScale = currentScale * scaleFactor

            if (newScale in 1.0f..5.0f) {
                matrixScale.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                imageMatrix = matrixScale
            }
            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            val values = FloatArray(9)
            matrixScale.getValues(values)
            val currentScale = values[Matrix.MSCALE_X]
            val newScale = if (currentScale > 1f) 1f else 5f

            matrixScale.setScale(newScale, newScale, e.x, e.y)
            imageMatrix = matrixScale
            return true
        }
    }

}
