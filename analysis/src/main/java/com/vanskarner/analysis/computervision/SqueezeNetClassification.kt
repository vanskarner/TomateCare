package com.vanskarner.analysis.computervision

import android.content.Context
import android.graphics.Bitmap
import com.vanskarner.analysis.ClassificationData
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.support.tensorbuffer.TensorBufferFloat
import java.nio.MappedByteBuffer
import kotlin.system.measureTimeMillis

internal fun useSqueezeNetMish(
    context: Context,
    imgBitmap: Bitmap,
    options: Interpreter.Options
): Result<Pair<Long, ClassificationData>> {
    try {
        val outputTensor = getOutputTensor(1)
        val timeInMillis = measureTimeMillis {
            val interpreter = Interpreter(getModel(context), options)
            val inputTensor = getInputTensor(imgBitmap)
            interpreter.run(inputTensor.buffer, outputTensor.buffer)
            interpreter.close()
        }
        val result = Pair(timeInMillis, createClassification(outputTensor.floatArray))
        return Result.success(result)
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

internal fun useSqueezeNetMish(
    context: Context,
    imgList: List<Bitmap>,
    options: Interpreter.Options
): Result<Pair<Long, List<ClassificationData>>> {
    try {
        val outputTensor = getOutputTensor(imgList.size)
        val timeInMillis = measureTimeMillis {
            val interpreter = Interpreter(getModel(context), options)
            val inputTensor = getInputTensor(imgList)
            interpreter.resizeInput(0, inputTensor.shape)
            interpreter.allocateTensors()
            interpreter.run(inputTensor.buffer, outputTensor.buffer)
            interpreter.close()
        }
        val classifications = outputTensor.floatArray.toList()
            .chunked(10)
            .map { createClassification(it.toFloatArray()) }
        val result = Pair(timeInMillis, classifications)
        return Result.success(result)
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

private fun getModel(context: Context): MappedByteBuffer =
    FileUtil.loadMappedFile(context, "Tomato_Disease-SqueezeNet_Mish.tflite")

private fun getInputTensor(imgBitmap: Bitmap): TensorBuffer {
    val shape = intArrayOf(
        1,//batch size is equal to the number of images to be predicted
        CLASSIFICATION_IMG_SIZE,
        CLASSIFICATION_IMG_SIZE,
        CLASSIFICATION_CHANNELS
    )
    val inputTensor = TensorBufferFloat.createFixedSize(shape, CLASSIFICATION_INPUT_IMAGE_TYPE)
    inputTensor.loadBuffer(bitmapToByteBuffer(imgBitmap))
    return inputTensor
}

private fun getInputTensor(imgList: List<Bitmap>): TensorBuffer {
    val shape = intArrayOf(
        imgList.size,//batch size is equal to the number of images to be predicted
        CLASSIFICATION_IMG_SIZE,
        CLASSIFICATION_IMG_SIZE,
        CLASSIFICATION_CHANNELS
    )
    val inputTensor = TensorBufferFloat.createFixedSize(shape, CLASSIFICATION_INPUT_IMAGE_TYPE)
    inputTensor.loadBuffer(bitmapListToByteBuffer(imgList))
    return inputTensor
}

private fun getOutputTensor(batchSize: Int): TensorBuffer {
    val outputShape = intArrayOf(batchSize, CLASSIFICATION_NUM_CLASSES)
    return TensorBufferFloat.createFixedSize(outputShape, CLASSIFICATION_OUTPUT_IMAGE_TYPE)
}

private fun createClassification(predictions: FloatArray): ClassificationData {
    val labeledPredictions = pairsLabelPrediction(predictions)
    val bestPrediction = getTopPrediction(labeledPredictions)
    val isHealthy = bestPrediction.first == CLASSIFICATION_CLASSES[2]
    return if (isHealthy)
        ClassificationData.healthy(
            bestPrediction,
            labeledPredictions
        )
    else ClassificationData.sick(bestPrediction, labeledPredictions)
}