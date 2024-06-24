package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.vanskarner.analysistracking.LeafState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.tensorflow.lite.Interpreter

@OptIn(ExperimentalCoroutinesApi::class)
class NASNetMobileClassificationDataTest {

    companion object {
        private lateinit var appContext: Context
        private lateinit var interpreterOptions: Interpreter.Options

        @BeforeClass
        @JvmStatic
        fun setUpBeforeClass() {
            appContext = InstrumentationRegistry.getInstrumentation().targetContext
            //The tests supported CPU
            interpreterOptions = Interpreter
                .Options()
                .setNumThreads(4)
        }
    }

    @Test
    fun predictionUsingInterpreterWithBacterialSpot() = runTest {
        val image = loadImage("bacterial_spot.jpg")
        val predictionResult = useNASNetMobile(
            appContext, image,
            interpreterOptions
        )
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("bacterial_spot", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithEarlyBlight() = runTest {
        val image = loadImage("early_blight.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("early_blight", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithHealthy() = runTest {
        val image = loadImage("healthy.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Healthy, classification.leafState)
        assertEquals("healthy", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithLateBlight() = runTest {
        val image = loadImage("late_blight.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("late_blight", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithLeafMold() = runTest {
        val image = loadImage("leaf_mold.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("leaf_mold", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithMosaicVirus() = runTest {
        val image = loadImage("mosaic_virus.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("mosaic_virus", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithSeptoriaLeafSpot() = runTest {
        val image = loadImage("septoria_leaf_spot.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("septoria_leaf_spot", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithTargetSpot() = runTest {
        val image = loadImage("target_spot.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("target_spot", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithTwoSpottedSpiderMite() = runTest {
        val image = loadImage("twospotted_spider_mite.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("twospotted_spider_mite", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithYellowLeafCurlVirus() = runTest {
        val image = loadImage("yellow_leaf_curl_virus.jpg")
        val predictionResult =
            useNASNetMobile(appContext, image, interpreterOptions)
        val result = predictionResult.getOrThrow()
        val inferenceTime = result.first
        val classification = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue(
            "Inference was $inferenceTime ms",
            inferenceTime in 50..2000
        )
        assertEquals(LeafState.Sick, classification.leafState)
        assertEquals("yellow_leaf_curl_virus", classification.bestPrediction.first)
        assertTrue(classification.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, classification.predictions.size)
    }

    @Test
    fun predictionUsingInterpreterWithImageList() = runTest {
        val imgList = listOf(
            loadImage("bacterial_spot.jpg"),
            loadImage("early_blight.jpg"),
            loadImage("healthy.jpg"),
            loadImage("late_blight.jpg"),
            loadImage("leaf_mold.jpg"),
            loadImage("mosaic_virus.jpg"),
            loadImage("septoria_leaf_spot.jpg"),
            loadImage("target_spot.jpg"),
            loadImage("twospotted_spider_mite.jpg"),
            loadImage("yellow_leaf_curl_virus.jpg")
        )
        val result = useNASNetMobile(appContext, imgList, interpreterOptions).getOrThrow()
        val inferenceTime = result.first
        val predictionResults = result.second

        //Inference time depends on hardware,change it according to case
        assertTrue("Inference was $inferenceTime ms", inferenceTime in 50..10000)
        println("Image batch inference time: $inferenceTime ms")

        //There should be the same number of predictions as the number of images entered
        assertEquals(imgList.size, predictionResults.size)

        //For bacterial_spot
        assertEquals(LeafState.Sick, predictionResults[0].leafState)
        assertEquals("bacterial_spot", predictionResults[0].bestPrediction.first)
        assertTrue(predictionResults[0].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[0].predictions.size)

        //For early_blight
        assertEquals(LeafState.Sick, predictionResults[1].leafState)
        assertEquals("early_blight", predictionResults[1].bestPrediction.first)
        assertTrue(predictionResults[1].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[1].predictions.size)

        //For healthy
        assertEquals(LeafState.Healthy, predictionResults[2].leafState)
        assertEquals("healthy", predictionResults[2].bestPrediction.first)
        assertTrue(predictionResults[2].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[2].predictions.size)

        //For late_blight
        assertEquals(LeafState.Sick, predictionResults[3].leafState)
        assertEquals("late_blight", predictionResults[3].bestPrediction.first)
        assertTrue(predictionResults[3].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[3].predictions.size)

        //For leaf_mold
        assertEquals(LeafState.Sick, predictionResults[4].leafState)
        assertEquals("leaf_mold", predictionResults[4].bestPrediction.first)
        assertTrue(predictionResults[4].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[4].predictions.size)

        //For mosaic_virus
        assertEquals(LeafState.Sick, predictionResults[5].leafState)
        assertEquals("mosaic_virus", predictionResults[5].bestPrediction.first)
        assertTrue(predictionResults[5].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[5].predictions.size)

        //For septoria_leaf_spot
        assertEquals(LeafState.Sick, predictionResults[6].leafState)
        assertEquals("septoria_leaf_spot", predictionResults[6].bestPrediction.first)
        assertTrue(predictionResults[6].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[6].predictions.size)

        //For target_spot
        assertEquals(LeafState.Sick, predictionResults[7].leafState)
        assertEquals("target_spot", predictionResults[7].bestPrediction.first)
        assertTrue(predictionResults[7].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[7].predictions.size)

        //For twospotted_spider_mite
        assertEquals(LeafState.Sick, predictionResults[8].leafState)
        assertEquals("twospotted_spider_mite", predictionResults[8].bestPrediction.first)
        assertTrue(predictionResults[8].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[8].predictions.size)

        //For yellow_leaf_curl_virus
        assertEquals(LeafState.Sick, predictionResults[9].leafState)
        assertEquals("yellow_leaf_curl_virus", predictionResults[9].bestPrediction.first)
        assertTrue(predictionResults[9].bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, predictionResults[9].predictions.size)
    }

    @Throws(Exception::class)
    private fun loadImage(fileName: String): Bitmap {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val assetManager = instrumentation.context.assets
        val inputStream = assetManager.open(fileName)
        return BitmapFactory.decodeStream(inputStream)
    }

}