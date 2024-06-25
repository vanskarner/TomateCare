package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.bussineslogic.DiseaseClassification
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer

class DefaultDiseaseClassification(private val context: Context) : DiseaseClassification {

    override suspend fun predict(byteBuffer: ByteBuffer): Result<ClassificationData> {
        TODO()
    }

    override suspend fun detectLeaves(imgPath: String): Pair<Long, List<BoundingBoxData>> {
        val imgBitmap = BitmapFactory.decodeFile(imgPath)
        val options = Interpreter
            .Options()
            .setNumThreads(4)
        return useYoloV8LeafDetection(context, imgBitmap, options)
    }

    override suspend fun classifyLeavesWithMobileNetV3Small(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun classifyLeavesWithMobileNetV3Large(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun classifyLeavesWithMobileNetV2(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun classifyLeavesWithSqueezeNetMish(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun classifyLeavesWithNASNetMobile(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        TODO("Not yet implemented")
    }

    fun classifyLeaf(
        imgPath: String,
        boundingBoxes: List<BoundingBoxData>
    ): Result<Pair<Long, List<ClassificationData>>> {
        val options = Interpreter
            .Options()
            .setNumThreads(4)
        val imgBitmap = BitmapFactory.decodeFile(imgPath)
        val imgList = boundingBoxes.map { cropImageFromBoundingBox(imgBitmap, it) }
        return useMobilenetV2(context, imgList, options)
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