package com.vanskarner.analysistracking.computervision

import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.exp
import kotlin.math.pow

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