package com.vanskarner.analysis.computervision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.bussineslogic.ComputerVision
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate

class TFLiteComputerVision(private val context: Context) : ComputerVision {

    override suspend fun detectLeaves(imgPath: String): Result<Pair<Long, List<BoundingBoxData>>> {
        val compatList = CompatibilityList()
        val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
        val imgBitmap = BitmapFactory.decodeFile(imgPath)
        if (isDelegateSupportedOnThisDevice) {
            val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
            val options = Interpreter.Options().addDelegate(delegate)
            val result = useYoloV8LeafDetection(context, imgBitmap, options)
            compatList.close()
            delegate.close()
            return result
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val options = Interpreter.Options().setNumThreads(numThreads)
        val result = useYoloV8LeafDetection(context, imgBitmap, options)
        compatList.close()
        return result
    }

    override suspend fun classifyLeavesWithMobileNetV3Small(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        val bitmapImage = BitmapFactory.decodeFile(imgPath)
        val bitmapImages = list
            .map { cropImageFromBoundingBox(bitmapImage, it) }
            .map { resizeImage(it) }
        if (useGPU) {
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                val options = Interpreter.Options().addDelegate(delegate)
                val result = useMobilenetV3Small(context, bitmapImages, options)
                compatList.close()
                delegate.close()
                return result
            }
            return Result.failure(AnalysisError.GPUNotSupportedByDevice)
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val option = Interpreter.Options().setNumThreads(numThreads)
        return useMobilenetV3Small(context, bitmapImages, option)
    }

    override suspend fun classifyLeavesWithMobileNetV3Large(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        val bitmapImage = BitmapFactory.decodeFile(imgPath)
        val bitmapImages = list
            .map { cropImageFromBoundingBox(bitmapImage, it) }
            .map { resizeImage(it) }
        if (useGPU) {
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                val options = Interpreter.Options().addDelegate(delegate)
                val result = useMobilenetV3Large(context, bitmapImages, options)
                compatList.close()
                delegate.close()
                return result
            }
            return Result.failure(AnalysisError.GPUNotSupportedByDevice)
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val option = Interpreter.Options().setNumThreads(numThreads)
        return useMobilenetV3Large(context, bitmapImages, option)
    }

    override suspend fun classifyLeavesWithMobileNetV2(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        val bitmapImage = BitmapFactory.decodeFile(imgPath)
        val bitmapImages = list
            .map { cropImageFromBoundingBox(bitmapImage, it) }
            .map { resizeImage(it) }
        if (useGPU) {
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                val options = Interpreter.Options().addDelegate(delegate)
                val result = useMobilenetV2(context, bitmapImages, options)
                compatList.close()
                delegate.close()
                return result
            }
            return Result.failure(AnalysisError.GPUNotSupportedByDevice)
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val option = Interpreter.Options().setNumThreads(numThreads)
        return useMobilenetV2(context, bitmapImages, option)
    }

    override suspend fun classifyLeavesWithSqueezeNetMish(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        val bitmapImage = BitmapFactory.decodeFile(imgPath)
        val bitmapImages = list
            .map { cropImageFromBoundingBox(bitmapImage, it) }
            .map { resizeImage(it) }
        if (useGPU) {
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                val options = Interpreter.Options().addDelegate(delegate)
                val result = useSqueezeNetMish(context, bitmapImages, options)
                compatList.close()
                delegate.close()
                return result
            }
            return Result.failure(AnalysisError.GPUNotSupportedByDevice)
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val option = Interpreter.Options().setNumThreads(numThreads)
        return useSqueezeNetMish(context, bitmapImages, option)
    }

    override suspend fun classifyLeavesWithNASNetMobile(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        val bitmapImage = BitmapFactory.decodeFile(imgPath)
        val bitmapImages = list
            .map { cropImageFromBoundingBox(bitmapImage, it) }
            .map { resizeImage(it) }
        if (useGPU) {
            val compatList = CompatibilityList()
            if (compatList.isDelegateSupportedOnThisDevice) {
                val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                val options = Interpreter.Options().addDelegate(delegate)
                val result = useNASNetMobile(context, bitmapImages, options)
                compatList.close()
                delegate.close()
                return result
            }
            return Result.failure(AnalysisError.GPUNotSupportedByDevice)
        }
        val numThreads = Runtime.getRuntime().availableProcessors()
        val option = Interpreter.Options().setNumThreads(numThreads)
        return useNASNetMobile(context, bitmapImages, option)
    }

    private fun cropImageFromBoundingBox(imgBitmap: Bitmap, boundingBox: BoundingBoxData): Bitmap {
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