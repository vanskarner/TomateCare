package com.vanskarner.analysis.computervision

import android.graphics.Bitmap
import com.vanskarner.analysis.BoundingBoxData
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.exp
import kotlin.math.pow

internal fun resizeImage(image: Bitmap) =
    Bitmap.createScaledBitmap(image, CLASSIFICATION_IMG_SIZE, CLASSIFICATION_IMG_SIZE, false)

internal fun softmax(predictions: FloatArray): FloatArray {
    val max = predictions.maxOrNull() ?: 0.0f
    val expValues = predictions.map { exp((it - max)) }
    val sumExpValues = expValues.sum()
    return expValues.map { (it / sumExpValues) }.toFloatArray()
}

internal fun pairsLabelPrediction(softmaxPredictions: FloatArray): List<Pair<String, Float>> {
    return CLASSIFICATION_CLASSES.zip(softmaxPredictions.toList()) { disease, probability ->
        val formattedProbability = formatToNDecimals(probability, 6)
        disease to formattedProbability
    }
}

internal fun getTopPrediction(labeledPredictions: List<Pair<String, Float>>): Pair<String, Float> {
    val bestPrediction = labeledPredictions.maxBy { it.second }
    val formattedPrediction =
        if (bestPrediction.second <= 1.0f) 0.99f
        else formatToNDecimals(bestPrediction.second, 2)
    return Pair(bestPrediction.first, formattedPrediction)
}

internal fun formatToNDecimals(value: Float, decimals: Int): Float {
    val factor = 10.0.pow(decimals.toDouble()).toFloat()
    return (value * factor).toInt().toFloat() / factor
}

internal fun bitmapListToByteBuffer(imgList: List<Bitmap>): ByteBuffer {
    val numImages = imgList.size //batch size
    val bytesPerChannel = 4 // float have 4 bytes
    val shape = CLASSIFICATION_IMG_SIZE * CLASSIFICATION_IMG_SIZE * CLASSIFICATION_CHANNELS
    val byteBufferCapacity = numImages * bytesPerChannel * shape
    val byteBuffer = ByteBuffer.allocateDirect(byteBufferCapacity)
    byteBuffer.order(ByteOrder.nativeOrder())
    val intValues = IntArray(CLASSIFICATION_IMG_SIZE * CLASSIFICATION_IMG_SIZE)
    for (image in imgList) {
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        for (i in 0 until CLASSIFICATION_IMG_SIZE) {
            for (j in 0 until CLASSIFICATION_IMG_SIZE) {
                val `val` = intValues[pixel++]
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }
    }
    /*byteBuffer.rewind()*/
    return byteBuffer
}

internal fun bitmapToByteBuffer(image: Bitmap): ByteBuffer {
    val bytesPerChannel = 4 // float have 4 bytes
    val shape = CLASSIFICATION_IMG_SIZE * CLASSIFICATION_IMG_SIZE * CLASSIFICATION_CHANNELS
    val byteBufferCapacity = bytesPerChannel * shape
    val byteBuffer = ByteBuffer.allocateDirect(byteBufferCapacity)
    byteBuffer.order(ByteOrder.nativeOrder())
    val intValues = IntArray(CLASSIFICATION_IMG_SIZE * CLASSIFICATION_IMG_SIZE)
    image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
    var pixel = 0
    //iterate over each pixel and extract R, G, and B values.
    // Add those values individually to the byte buffer.
    for (i in 0 until CLASSIFICATION_IMG_SIZE) {
        for (j in 0 until CLASSIFICATION_IMG_SIZE) {
            val `val` = intValues[pixel++] // RGB
            byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
            byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
            byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
        }
    }
    /*byteBuffer.rewind()*/
    return byteBuffer
}

/**
 * Function for object detection, extracts and returns a list of bounding boxes from an array
 * of predictions. It filters the boxes by a confidence threshold and normalizes the coordinates
 * of the boxes within the range [0, 1]. It then applies non-maximum suppression
 * to eliminate redundant boxes.
 */
internal fun createBoundingBoxes(
    array: FloatArray,
    numElements: Int,
    numChannel: Int,
    confidenceThreshold: Float,
    iouThreshold: Float,
    classNames: List<String>
): List<BoundingBoxData> {
    val boundingBoxData = mutableListOf<BoundingBoxData>()
    for (c in 0 until numElements) {
        var maxConf = confidenceThreshold
        var maxIdx = -1
        for (j in 4 until numChannel) {
            val conf = array[c + numElements * j]
            if (conf > maxConf) {
                maxConf = conf
                maxIdx = j - 4
            }
        }
        if (maxConf > confidenceThreshold) {
            val cx = array[c]
            val cy = array[c + numElements]
            val w = array[c + numElements * 2]
            val h = array[c + numElements * 3]
            val x1 = cx - w / 2
            val y1 = cy - h / 2
            val x2 = cx + w / 2
            val y2 = cy + h / 2
            if (x1 in 0f..1f && y1 in 0f..1f && x2 in 0f..1f && y2 in 0f..1f) {
                val item = BoundingBoxData(
                    x1 = x1, y1 = y1, x2 = x2, y2 = y2,
                    cx = cx, cy = cy, w = w, h = h,
                    cnf = maxConf, cls = maxIdx, clsName = classNames[maxIdx]
                )
                boundingBoxData.add(item)
            }
        }
    }
    return if (boundingBoxData.isEmpty()) emptyList() else applyNMS(boundingBoxData, iouThreshold)
}

/**
 * Function for object detection, this function filters out overlapping boxes
 * to keep only the most reliable and non-redundant ones
 */
private fun applyNMS(boxes: List<BoundingBoxData>, iouThreshold: Float): List<BoundingBoxData> {
    val sortedBoxes = boxes.sortedByDescending { it.cnf }.toMutableList()
    val selectedBoxes = mutableListOf<BoundingBoxData>()
    while (sortedBoxes.isNotEmpty()) {
        val first = sortedBoxes.first()
        selectedBoxes.add(first)
        sortedBoxes.remove(first)
        val iterator = sortedBoxes.iterator()
        while (iterator.hasNext()) {
            val nextBox = iterator.next()
            val iou = calculateIoU(first, nextBox)
            if (iou >= iouThreshold) {
                iterator.remove()
            }
        }
    }
    return selectedBoxes
}

/**
 * Function for object detection, calculate the Intersection over Union (IoU)
 * between two bounding boxes
 */
private fun calculateIoU(box1: BoundingBoxData, box2: BoundingBoxData): Float {
    val x1 = maxOf(box1.x1, box2.x1)
    val y1 = maxOf(box1.y1, box2.y1)
    val x2 = minOf(box1.x2, box2.x2)
    val y2 = minOf(box1.y2, box2.y2)
    val intersectionArea = maxOf(0F, x2 - x1) * maxOf(0F, y2 - y1)
    val box1Area = box1.w * box1.h
    val box2Area = box2.w * box2.h
    return intersectionArea / (box1Area + box2Area - intersectionArea)
}