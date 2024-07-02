package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.SetConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class ClassifyLeavesUseCaseTest {

    @Test
    fun `execute there are no leaves return NoLeavesError`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = emptyList<BoundingBoxData>()
        val validConfig = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2",
        )
        val exception = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfig)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.NoLeaves)
    }

    @Test
    fun `execute with invalidConfig return InvalidConfigError`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfig = SetConfigData(
            numberResults = 0,
            numberThreads = 0,
            processing = "unknown",
            model = "unknown",
        )
        val exception = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfig)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.InvalidConfig)
    }

    @Test
    fun `execute configured to use MobileNetV2 return classifications`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2",
        )
        val expectedUseGPU = validConfigData.processing == "GPU"
        val expectedResult = createClassifications(createPredictions())
        `when`(
            computerVision.classifyLeavesWithMobileNetV2(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedResult))
        val actualClassifications = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfigData)
            .getOrThrow()

        verify(computerVision, times(1))
            .classifyLeavesWithMobileNetV2(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        assertEquals(expectedResult, actualClassifications)
    }

    @Test
    fun `execute configured to use MobileNetV3Large return classifications`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV3Large",
        )
        val expectedUseGPU = validConfigData.processing == "GPU"
        val expectedResult = createClassifications(createPredictions())
        `when`(
            computerVision.classifyLeavesWithMobileNetV3Large(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedResult))
        val actualClassifications = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfigData)
            .getOrThrow()

        verify(computerVision, times(1))
            .classifyLeavesWithMobileNetV3Large(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        assertEquals(expectedResult, actualClassifications)
    }

    @Test
    fun `execute configured to use MobileNetV3Small return classifications`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV3Small",
        )
        val expectedUseGPU = validConfigData.processing == "GPU"
        val expectedResult = createClassifications(createPredictions())
        `when`(
            computerVision.classifyLeavesWithMobileNetV3Small(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedResult))
        val actualClassifications = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfigData)
            .getOrThrow()

        verify(computerVision, times(1))
            .classifyLeavesWithMobileNetV3Small(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        assertEquals(expectedResult, actualClassifications)
    }

    @Test
    fun `execute configured to use SqueezeNetMish return classifications`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "SqueezeNetMish",
        )
        val expectedUseGPU = validConfigData.processing == "GPU"
        val expectedResult = createClassifications(createPredictions())
        `when`(
            computerVision.classifyLeavesWithSqueezeNetMish(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedResult))
        val actualClassifications = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfigData)
            .getOrThrow()

        verify(computerVision, times(1))
            .classifyLeavesWithSqueezeNetMish(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        assertEquals(expectedResult, actualClassifications)
    }

    @Test
    fun `execute configured to use NASNetMobile return classifications`() = runTest {
        val validateConfigUseCase = ValidateConfigUseCase()
        val computerVision: ComputerVision = mock()
        val expectedImgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val expectedBoundingBoxes = listOf(createBoundingBoxData())
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "NASNetMobile",
        )
        val expectedUseGPU = validConfigData.processing == "GPU"
        val expectedResult = createClassifications(createPredictions())
        `when`(
            computerVision.classifyLeavesWithNASNetMobile(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedResult))
        val actualClassifications = ClassifyLeavesUseCase(validateConfigUseCase, computerVision)
            .execute(expectedImgPath, expectedBoundingBoxes, validConfigData)
            .getOrThrow()

        verify(computerVision, times(1))
            .classifyLeavesWithNASNetMobile(
                expectedImgPath,
                expectedBoundingBoxes,
                expectedUseGPU,
                validConfigData.numberThreads
            )
        assertEquals(expectedResult, actualClassifications)
    }

    private fun createBoundingBoxData(): BoundingBoxData {
        return BoundingBoxData(
            0.00333f,
            0.00333f,
            0.00333f,
            0.00333f,
            0.00333f,
            0.00333f,
            0.00333f,
            0.00333f,
            0.95f,
            1,
            "leaf"
        )
    }

    private fun createPredictions() = listOf(
        Pair("bacterial_spot", 0.95f),
        Pair("early_blight", 0.01f),
        Pair("healthy", 0.01f),
        Pair("late_blight", 0.01f),
        Pair("leaf_mold", 0.01f),
        Pair("mosaic_virus", 0.01f),
        Pair("septoria_leaf_spot", 0f),
        Pair("target_spot", 0f),
        Pair("twospotted_spider_mite", 0f),
        Pair("yellow_leaf_curl_virus", 0f),
    )

    private fun createClassifications(predictions: List<Pair<String, Float>>)
            : Pair<Long, List<ClassificationData>> {
        val inferenceTime = 2000L
        val bestPrediction = Pair("bacterial_spot", 0.95f)
        val classifications = listOf(
            ClassificationData.sick(bestPrediction, predictions)
        )
        return Pair(inferenceTime, classifications)
    }
}