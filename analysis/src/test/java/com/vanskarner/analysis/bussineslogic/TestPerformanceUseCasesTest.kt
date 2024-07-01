package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.TestConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.Mockito.`when`
import java.io.ByteArrayInputStream
import java.io.InputStream

class TestPerformanceUseCasesTest {

    @Test
    fun `testPerformanceUseCase with validConfig return inference times`() = runTest {
        val computerVisionPerformance: ComputerVisionPerformance = mock()
        val validConfig = TestConfigData(
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2"
        )
        val expectedDetectionInference = 2000L
        val expectedClassificationInference = 2500L
        val expectedUseGPU = validConfig.processing == "GPU"
        val expectedThreads = validConfig.numberThreads
        `when`(computerVisionPerformance.testYoloV8nModel(expectedUseGPU, expectedThreads))
            .thenReturn(Result.success(expectedDetectionInference))
        `when`(computerVisionPerformance.testMobileNetV2Model(expectedUseGPU, expectedThreads))
            .thenReturn(Result.success(expectedClassificationInference))
        val testPerformanceUseCase = TestPerformanceUseCase(
            computerVisionPerformance, validateConfigUseCase = ValidateConfigUseCase()
        )
        val result = testPerformanceUseCase.execute(validConfig).getOrThrow()
        val actualDetectionInference = result.first
        val actualClassificationInference = result.second

        verify(computerVisionPerformance, times(1))
            .testYoloV8nModel(expectedUseGPU, expectedThreads)
        verify(computerVisionPerformance, times(1))
            .testMobileNetV2Model(expectedUseGPU, expectedThreads)
        assertEquals(expectedDetectionInference, actualDetectionInference)
        assertEquals(expectedClassificationInference, actualClassificationInference)
    }

    @Test
    fun `GetTestResourcesUseCase with validConfig return inference times`() = runTest {
        val computerVisionPerformance: ComputerVisionPerformance = mock()
        val expectedInputStreamDetection: InputStream =
            ByteArrayInputStream("test InputStream for Detection".toByteArray())
        val expectedInputStreamClassification: InputStream =
            ByteArrayInputStream("test InputStream for Classification".toByteArray())
        `when`(computerVisionPerformance.imageUsedForDetection())
            .thenReturn(expectedInputStreamDetection)
        `when`(computerVisionPerformance.imagesUsedForClassification())
            .thenReturn(expectedInputStreamClassification)
        val getTestResourcesUseCase = GetTestResourcesUseCase(computerVisionPerformance)
        val result = getTestResourcesUseCase.execute().getOrThrow()
        val actualInputStreamDetection = result.first
        val actualInputStreamClassification = result.second

        verify(computerVisionPerformance, times(1))
            .imageUsedForDetection()
        verify(computerVisionPerformance, times(1))
            .imagesUsedForClassification()
        assertEquals(expectedInputStreamDetection, actualInputStreamDetection)
        assertEquals(expectedInputStreamClassification, actualInputStreamClassification)
    }

}