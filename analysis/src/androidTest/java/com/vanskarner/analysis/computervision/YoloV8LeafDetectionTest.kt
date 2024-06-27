package com.vanskarner.analysis.computervision

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.tensorflow.lite.Interpreter

class YoloV8LeafDetectionTest {

    @Test
    fun predictionUsingModelFloat16() = runTest {
        val interpreterOptions = Interpreter //The tests supported CPU
            .Options()
            .setNumThreads(4)
        val image = loadImageExample()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val result = useYoloV8LeafDetection(appContext, image, interpreterOptions).getOrThrow()

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was ${result.first} ms",
            result.first in 100..4000
        )
        assertEquals(4, result.second.size)
    }

    @Throws(Exception::class)
    private fun loadImageExample(): Bitmap {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val assetManager = instrumentation.context.assets
        val inputStream = assetManager.open("leaf_detection.jpg")
        return BitmapFactory.decodeStream(inputStream)
    }

}