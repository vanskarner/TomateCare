package com.vanskarner.analysistracking.computervision

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.vanskarner.analysistracking.LeafState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MobileNetV2Test {

    @Test
    fun predictionWithBacterialSpot() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("bacterial_spot.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("bacterial_spot", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithEarlyBlight() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("early_blight.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("early_blight", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithHealthy() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("healthy.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Healthy, result.leafState)
        assertEquals("Healthy", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.99f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithLateBlight() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("late_blight.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("late_blight", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithLeafMold() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("leaf_mold.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("leaf_mold", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithMosaicVirus() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("mosaic_virus.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("mosaic_virus", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithSeptoriaLeafSpot() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("septoria_leaf_spot.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("septoria_leaf_spot", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithTargetSpot() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("target_spot.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("target_spot", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithTwoSpottedSpiderMite() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("twospotted_spider_mite.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("twospotted_spider_mite", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Test
    fun predictionWithYellowLeafCurlVirus() = runTest {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bacterialSpotImage = loadImage("yellow_leaf_curl_virus.jpg")
        val predictionResult = useMobileNetV2(appContext, bacterialSpotImage)
        val result = predictionResult.getOrThrow()

        assertEquals(LeafState.Sick, result.leafState)
        assertEquals("yellow_leaf_curl_virus", result.bestPrediction.first)
        assertTrue(result.bestPrediction.second in 0.90f..0.990f)
        assertEquals(10, result.predictions.size)
    }

    @Throws(Exception::class)
    private fun loadImage(fileName: String): Bitmap {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val assetManager = instrumentation.context.assets
        val inputStream = assetManager.open(fileName)
        return BitmapFactory.decodeStream(inputStream)
    }

}