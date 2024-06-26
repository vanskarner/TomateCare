package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import com.vanskarner.analysistracking.Classification
import com.vanskarner.analysistracking.ClassificationByBatch
import com.vanskarner.analysistracking.ClassificationItem
import com.vanskarner.analysistracking.ml.TomatoDiseaseMobilenetv2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.support.tensorbuffer.TensorBufferFloat
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.system.measureTimeMillis

internal fun useMobilenetV2WithAutogeneratedModel(
    context: Context,
    imgBitmap: Bitmap,
    options: Model.Options,
): Result<Classification> {
    try {
        val outputTensor: TensorBuffer
        val timeInMillis = measureTimeMillis {
            val model = TomatoDiseaseMobilenetv2.newInstance(context, options)
            val shape = intArrayOf(
                1,//batch size is equal to the number of images to be predicted
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_CHANNELS
            )
            val inputFeature0 = TensorBuffer.createFixedSize(shape, DataType.FLOAT32)
            val imgByteBuffer = bitmapToByteBuffer(imgBitmap)
            inputFeature0.loadBuffer(imgByteBuffer)
            outputTensor = model.process(inputFeature0).outputFeature0AsTensorBuffer
            model.close()
        }
        val predictionsWithSoftmax = softmax(outputTensor.floatArray)
        val labeledPredictions = pairsLabelPrediction(predictionsWithSoftmax)
        val topPrediction = getTopPrediction(labeledPredictions)
        return if (topPrediction.first == CLASSIFICATION_CLASSES[2])
            Result.success(
                Classification.healthy(
                    topPrediction.second,
                    timeInMillis,
                    labeledPredictions
                )
            )
        else Result.success(Classification.sick(topPrediction, timeInMillis, labeledPredictions))

    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

internal fun useMobilenetV2WithAutogeneratedModel(
    context: Context,
    imgList: List<Bitmap>,
    options: Model.Options
): Result<ClassificationByBatch> {
    try {
        val floatArrayList = mutableListOf<FloatArray>()
        val timeInMillis = measureTimeMillis {
            val model = TomatoDiseaseMobilenetv2.newInstance(context, options)
            val shape = intArrayOf(
                1,//batch size is equal to the number of images to be predicted
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_CHANNELS
            )
            val inputFeature0 = TensorBuffer.createFixedSize(shape, DataType.FLOAT32)
            imgList.forEach { imgItem ->
                val imgByteBuffer = bitmapToByteBuffer(imgItem)
                inputFeature0.loadBuffer(imgByteBuffer)
                val tensorBuffer = model.process(inputFeature0).outputFeature0AsTensorBuffer
                floatArrayList.add(tensorBuffer.floatArray)
            }
            model.close()
        }
        val predictions = mutableListOf<ClassificationItem>()
        floatArrayList.forEach {
            val softmaxPredictions = softmax(it)
            val labeledPredictions = pairsLabelPrediction(softmaxPredictions)
            val topPrediction = getTopPrediction(labeledPredictions)
            if (topPrediction.first == CLASSIFICATION_CLASSES[2])
                predictions.add(
                    ClassificationItem.healthy(
                        topPrediction.second,
                        labeledPredictions
                    )
                )
            else predictions.add(ClassificationItem.sick(topPrediction, labeledPredictions))
        }
        return Result.success(ClassificationByBatch(timeInMillis, predictions))
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

internal fun useMobilenetV2WithInterpreter(
    context: Context,
    imgBitmap: Bitmap,
    options: Interpreter.Options
): Result<Classification> {
    try {
        val outputTensor: TensorBuffer
        val timeInMillis = measureTimeMillis {
            val interpreter = Interpreter(loadModelFile(context),options)
            val shape = intArrayOf(
                1,//batch size is equal to the number of images to be predicted
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_CHANNELS
            )
            val inputTensor = TensorBufferFloat.createFixedSize(shape, DataType.FLOAT32)
            inputTensor.loadBuffer(bitmapToByteBuffer(imgBitmap))
            val outputShape = intArrayOf(1, 10)
            outputTensor = TensorBufferFloat.createFixedSize(outputShape, DataType.FLOAT32)
            interpreter.run(inputTensor.buffer, outputTensor.buffer)
            interpreter.close()
        }
        val softmaxPredictions = softmax(outputTensor.floatArray)
        val labeledPredictions = pairsLabelPrediction(softmaxPredictions)
        val topPrediction = getTopPrediction(labeledPredictions)
        return if (topPrediction.first == CLASSIFICATION_CLASSES[2])
            Result.success(
                Classification.healthy(
                    topPrediction.second,
                    timeInMillis,
                    labeledPredictions
                )
            )
        else Result.success(Classification.sick(topPrediction, timeInMillis, labeledPredictions))
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

internal fun useMobilenetV2WithInterpreter(
    context: Context,
    imgList: List<Bitmap>,
    options: Interpreter.Options
): Result<ClassificationByBatch> {
    try {
        val outputTensor: TensorBuffer
        val timeInMillis = measureTimeMillis {
            val interpreter = Interpreter(loadModelFile(context),options)
            val batchSize = imgList.size
            val shape = intArrayOf(
                batchSize,//batch size is equal to the number of images to be predicted
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_IMG_SIZE,
                CLASSIFICATION_CHANNELS
            )
            val inputTensor = TensorBufferFloat.createFixedSize(shape, DataType.FLOAT32)
            interpreter.resizeInput(0, shape)
            interpreter.allocateTensors()
            inputTensor.loadBuffer(bitmapListToByteBuffer(imgList))
            val outputShape = intArrayOf(batchSize, CLASSIFICATION_NUM_CLASSES)
            outputTensor = TensorBufferFloat.createFixedSize(outputShape, DataType.FLOAT32)
            interpreter.run(inputTensor.buffer, outputTensor.buffer)
            interpreter.close()
        }
        val predictions = mutableListOf<ClassificationItem>()
        outputTensor.floatArray.toList().chunked(10).forEach {
            val softmaxPredictions = softmax(it.toFloatArray())
            val labeledPredictions = pairsLabelPrediction(softmaxPredictions)
            val topPrediction = getTopPrediction(labeledPredictions)
            if (topPrediction.first == CLASSIFICATION_CLASSES[2])
                predictions.add(
                    ClassificationItem.healthy(
                        topPrediction.second,
                        labeledPredictions
                    )
                )
            else predictions.add(ClassificationItem.sick(topPrediction, labeledPredictions))
        }
        return Result.success(ClassificationByBatch(timeInMillis, predictions))
    } catch (exception: Exception) {
        return Result.failure(exception)
    }
}

private fun loadModelFile(context: Context): MappedByteBuffer {
    val modelPath = "Tomato_Disease-MobilenetV2.tflite"
    val fileInputStream = FileInputStream(context.assets.openFd(modelPath).fileDescriptor)
    val fileChannel = fileInputStream.channel
    val startOffset = context.assets.openFd(modelPath).startOffset
    val declaredLength = context.assets.openFd(modelPath).declaredLength
    return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
}