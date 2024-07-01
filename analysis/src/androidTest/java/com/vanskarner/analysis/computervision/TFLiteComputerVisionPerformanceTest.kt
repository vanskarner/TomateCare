package com.vanskarner.analysis.computervision

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.vanskarner.analysis.AnalysisError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

class TFLiteComputerVisionPerformanceTest {

    companion object {
        private lateinit var appContext: Context
        private lateinit var computerVisionPerformance: TFLiteComputerVisionPerformance

        @JvmStatic
        @BeforeClass
        fun setUpBeforeClass() {
            /*Tests using GPU should return GPUNotSupportedByDevice error on this hardware*/
            appContext = InstrumentationRegistry.getInstrumentation().targetContext
            computerVisionPerformance = TFLiteComputerVisionPerformance(appContext)
        }
    }

    @Test
    fun testYoloV8nModel_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testYoloV8nModel(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testMobileNetV3SmallModel_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testMobileNetV3SmallModel(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testMobileNetV3LargeModel_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testMobileNetV3LargeModel(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testMobileNetV2Model_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testMobileNetV2Model(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testSqueezeNetMishModel_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testSqueezeNetMishModel(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testNASNetMobileModel_withGPUNotSupported_returnGPUNotSupportedByDevice() = runTest {
        val exception = computerVisionPerformance.testNASNetMobileModel(true, 4)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.GPUNotSupportedByDevice)
    }

    @Test
    fun testYoloV8nModel_withCPU_returnDetectionInferenceTime() = runTest {
        val detectionInferenceTime = computerVisionPerformance
            .testYoloV8nModel(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $detectionInferenceTime ms",
            detectionInferenceTime in 100..5000
        )
    }

    @Test
    fun testMobileNetV3SmallModel_withCPU_returnClassificationInferenceTime() = runTest {
        val classificationInferenceTime = computerVisionPerformance
            .testMobileNetV3SmallModel(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $classificationInferenceTime ms",
            classificationInferenceTime in 100..4000
        )
    }

    @Test
    fun testMobileNetV3LargeModel_withCPU_returnClassificationInferenceTime() = runTest {
        val classificationInferenceTime = computerVisionPerformance
            .testMobileNetV3LargeModel(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $classificationInferenceTime ms",
            classificationInferenceTime in 100..4000
        )
    }

    @Test
    fun testMobileNetV2Model_withCPU_returnClassificationInferenceTime() = runTest {
        val classificationInferenceTime = computerVisionPerformance
            .testMobileNetV2Model(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $classificationInferenceTime ms",
            classificationInferenceTime in 100..6000
        )
    }

    @Test
    fun testSqueezeNetMishModel_withCPU_returnClassificationInferenceTime() = runTest {
        val classificationInferenceTime = computerVisionPerformance
            .testSqueezeNetMishModel(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $classificationInferenceTime ms",
            classificationInferenceTime in 100..9500
        )
    }

    @Test
    fun testNASNetMobileModel_withCPU_returnClassificationInferenceTime() = runTest {
        val classificationInferenceTime = computerVisionPerformance
            .testNASNetMobileModel(false, 4)
            .getOrThrow()

        assertTrue(
            "Inference was $classificationInferenceTime ms",
            classificationInferenceTime in 100..10000
        )
    }

    @Test
    fun imageUsedForDetection_returnValidInputStream() = runTest {
        val inputStream = computerVisionPerformance.imageUsedForDetection()

        assertTrue(inputStream.available() > 0)
        inputStream.close()
    }

    @Test
    fun imagesUsedForClassification_returnValidInputStream() = runTest {
        val inputStream = computerVisionPerformance.imagesUsedForClassification()

        assertTrue(inputStream.available() > 0)
        inputStream.close()
    }

}