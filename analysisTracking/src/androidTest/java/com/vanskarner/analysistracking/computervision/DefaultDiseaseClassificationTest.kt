package com.vanskarner.analysistracking.computervision

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultDiseaseClassificationTest {

    @Test
    fun mobilenetv2_bacterial() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val diseaseClassification = DefaultDiseaseClassification(appContext)
        val image = loadImage("early_blight.jpg")
        val imgByteBuffer: ByteBuffer = bitmapToByteBuffer(image, 224)
        diseaseClassification.predict(imgByteBuffer)
            .onSuccess {
                println(it.allProbabilities.toString())
                assertEquals(10, it.allProbabilities.size)
            }
    }

    private fun bitmapToByteBuffer(image: Bitmap, imgSize: Int): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * imgSize * imgSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(imgSize * imgSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        //iterate over each pixel and extract R, G, and B values.
        // Add those values individually to the byte buffer.
        for (i in 0 until imgSize) {
            for (j in 0 until imgSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }
        return byteBuffer
    }

    @Throws(Exception::class)
    private fun loadImage(fileName: String): Bitmap {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val assetManager = instrumentation.context.assets
        val inputStream = assetManager.open(fileName)
        return BitmapFactory.decodeStream(inputStream)
    }
}