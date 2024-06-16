package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import com.vanskarner.analysistracking.Predictions
import com.vanskarner.analysistracking.ml.TomatoDiseaseMobilenetv2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.exp
import kotlin.math.pow

private const val IMG_SIZE = 224
private const val CHANNELS = 3
private val categories = listOf(
    "bacterial_spot",
    "early_blight",
    "healthy",
    "late_blight",
    "leaf_mold",
    "mosaic_virus",
    "septoria_leaf_spot",
    "target_spot",
    "twospotted_spider_mite",
    "yellow_leaf_curl_virus"
)

fun useMobileNetV2(context: Context, imgBitmap: Bitmap): Result<Predictions> {
    try {
        val model = TomatoDiseaseMobilenetv2.newInstance(context)
        val shape = intArrayOf(1, IMG_SIZE, IMG_SIZE, CHANNELS)
        val inputFeature0 = TensorBuffer.createFixedSize(shape, DataType.FLOAT32)
        val imgByteBuffer = bitmapToByteBuffer(imgBitmap)
        inputFeature0.loadBuffer(imgByteBuffer)
        val outputTensor = model.process(inputFeature0).outputFeature0AsTensorBuffer
        model.close()
        val predictionsWithSoftmax = softmax(outputTensor.floatArray)
        val labeledPredictions = pairsLabelPrediction(predictionsWithSoftmax)
        val topPrediction = getTopPrediction(labeledPredictions)
        return if (topPrediction.first == categories[2])
            Result.success(Predictions.healthy(topPrediction.second, labeledPredictions))
        else Result.success(Predictions.sick(topPrediction, labeledPredictions))
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

private fun softmax(predictions: FloatArray): FloatArray {
    val max = predictions.maxOrNull() ?: 0.0f
    val expValues = predictions.map { exp((it - max)) }
    val sumExpValues = expValues.sum()
    return expValues.map { (it / sumExpValues) }.toFloatArray()
}

private fun pairsLabelPrediction(predictionsSoftmax: FloatArray): List<Pair<String, Float>> {
    return categories.zip(predictionsSoftmax.toList()) { disease, probability ->
        val formattedProbability = formatToNDecimals(probability, 6)
        disease to formattedProbability
    }
}

private fun getTopPrediction(labeledPredictions: List<Pair<String, Float>>): Pair<String, Float> {
    val bestPrediction = labeledPredictions.maxBy { it.second }
    val formattedPrediction =
        if (bestPrediction.second <= 1.0f) 0.99f
        else formatToNDecimals(bestPrediction.second, 2)
    return Pair(bestPrediction.first, formattedPrediction)
}

private fun formatToNDecimals(value: Float, decimals: Int): Float {
    val factor = 10.0.pow(decimals.toDouble()).toFloat()
    return (value * factor).toInt().toFloat() / factor
}

private fun bitmapToByteBuffer(image: Bitmap): ByteBuffer {
    val byteBuffer = ByteBuffer.allocateDirect(4 * IMG_SIZE * IMG_SIZE * 3)
    byteBuffer.order(ByteOrder.nativeOrder())
    val intValues = IntArray(IMG_SIZE * IMG_SIZE)
    image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
    var pixel = 0
    //iterate over each pixel and extract R, G, and B values.
    // Add those values individually to the byte buffer.
    for (i in 0 until IMG_SIZE) {
        for (j in 0 until IMG_SIZE) {
            val `val` = intValues[pixel++] // RGB
            byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
            byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
            byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
        }
    }
    return byteBuffer
}
