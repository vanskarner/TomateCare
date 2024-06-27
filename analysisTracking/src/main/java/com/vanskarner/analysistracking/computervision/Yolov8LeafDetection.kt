package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.system.measureTimeMillis

private val CLASS_NAMES = listOf("leaf")
private val INPUT_IMAGE_TYPE = DataType.FLOAT32
private val OUTPUT_IMAGE_TYPE = DataType.FLOAT32
private const val CONFIDENCE_THRESHOLD = 0.42F
private const val IOU_THRESHOLD = 0.5F
private val IMAGE_PROCESSOR = ImageProcessor.Builder()
    .add(NormalizeOp(0f, 255f))
    .add(CastOp(INPUT_IMAGE_TYPE))
    .build()

internal fun useYoloV8LeafDetection(
    context: Context,
    imgBitmap: Bitmap,
    options: Interpreter.Options
) = runCatching {
    val model = FileUtil.loadMappedFile(context, "Leaf_Detection-YoloV8_float16.tflite")
    val interpreter = Interpreter(model, options)
    val outputTensor = getOutputTensor(interpreter)
    val inferenceTimeMilliSeconds = measureTimeMillis {
        val inputTensor = getInputTensor(imgBitmap, interpreter)
        interpreter.run(inputTensor.buffer, outputTensor.buffer)
        interpreter.close()
    }
    val boundingBoxes = createBoundingBoxes(
        array = outputTensor.floatArray,
        numElements = outputTensor.shape[2],
        numChannel = outputTensor.shape[1],
        confidenceThreshold = CONFIDENCE_THRESHOLD,
        iouThreshold = IOU_THRESHOLD,
        classNames = CLASS_NAMES
    )
    Pair(inferenceTimeMilliSeconds, boundingBoxes)
}

private fun getInputTensor(imgBitmap: Bitmap, interpreter: Interpreter): TensorBuffer {
    val inputShape = interpreter.getInputTensor(0).shape()
    val tensorWidth = inputShape[1]
    val tensorHeight = inputShape[2]
    val resizedBitmap = Bitmap.createScaledBitmap(imgBitmap, tensorWidth, tensorHeight, false)
    val tensorImage = TensorImage(INPUT_IMAGE_TYPE)
    tensorImage.load(resizedBitmap)
    return IMAGE_PROCESSOR.process(tensorImage).tensorBuffer
}

private fun getOutputTensor(interpreter: Interpreter): TensorBuffer {
    val outputShape = interpreter.getOutputTensor(0).shape()
    val numChannel = outputShape[1]
    val numElements = outputShape[2]
    val shape = intArrayOf(1, numChannel, numElements)
    return TensorBuffer.createFixedSize(shape, OUTPUT_IMAGE_TYPE)
}
