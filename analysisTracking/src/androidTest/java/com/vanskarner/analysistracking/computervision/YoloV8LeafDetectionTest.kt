package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.BeforeClass
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.model.Model

@OptIn(ExperimentalCoroutinesApi::class)
class YoloV8LeafDetectionTest {

    companion object {
        private lateinit var appContext: Context
        private lateinit var modelOptions: Model.Options
        private lateinit var interpreterOptions: Interpreter.Options

        @BeforeClass
        @JvmStatic
        fun setUpBeforeClass() {
            appContext = InstrumentationRegistry.getInstrumentation().targetContext
            //The tests supported CPU
            modelOptions = Model.Options.Builder()
                .setDevice(Model.Device.CPU)
                .setNumThreads(4)
                .build()
            //The tests supported CPU
            interpreterOptions = Interpreter
                .Options()
                .setNumThreads(4)
        }
    }

    @Test
    fun predictionUsingModelFloat16() = runTest {
        val image = loadImageExample()
        val result = useYoloV8LeafDetection(appContext, image, interpreterOptions)

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was ${result.first} ms",
            result.first in 100..3000
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