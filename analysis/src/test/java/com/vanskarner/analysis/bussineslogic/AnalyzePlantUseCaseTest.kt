package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.SetConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.util.Date

class AnalyzePlantUseCaseTest {

    @Test
    fun `AnalyzeImageUseCase with dataOK savedAnalysis`() = runTest {
        val repository: Repository = mock()
        val computerVision: ComputerVision = mock()
        val validateConfigUseCase = ValidateConfigUseCase()
        val useCase = AnalyzePlantUseCase(
            validateConfigUseCase = validateConfigUseCase,
            detectLeavesUseCase = DetectLeavesUseCase(computerVision),
            classifyLeavesUseCase = ClassifyLeavesUseCase(validateConfigUseCase, computerVision),
            repository = repository
        )
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2",
        )
        val expectedLeafDetection = createLeafDetection()
        val expectedLeafClassification = createLeafClassification(createPredictions())
        val expectedPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        `when`(computerVision.detectLeaves(expectedPath))
            .thenReturn(Result.success(expectedLeafDetection))
        `when`(
            computerVision.classifyLeavesWithMobileNetV2(
                imgPath = expectedPath,
                list = expectedLeafDetection.second,
                useGPU = validConfigData.processing == "GPU",
                numberThreads = validConfigData.numberThreads
            )
        ).thenReturn(Result.success(expectedLeafClassification))
        `when`(repository.saveAnalysis(any())).thenReturn(Result.success(1))

        val analysisDetailData = useCase.execute(expectedPath, validConfigData).getOrThrow()
        assertEquals(1, analysisDetailData.id)
        assertEquals(expectedPath, analysisDetailData.imagePath)
        assertTrue(
            "The date was not created in the last 100 ms",
            Date().time - analysisDetailData.date.time <= 100
        )
        assertEquals(expectedLeafDetection.first, analysisDetailData.detectionInferenceTimeMs)
        assertEquals(
            expectedLeafClassification.first,
            analysisDetailData.classificationInferenceTimeMs
        )
        assertEquals("", analysisDetailData.note)
        assertEquals(1, analysisDetailData.numberDiseasesIdentified)
        assertEquals(expectedLeafDetection.second, analysisDetailData.listLeafBoxCoordinates)
        assertEquals(expectedLeafClassification.second, analysisDetailData.classificationData)
        assertEquals("YoloV8n", analysisDetailData.leafDetectionModel)
        assertEquals(validConfigData.model, analysisDetailData.leafClassificationModel)
        assertEquals(validConfigData.numberThreads.toString(), analysisDetailData.threadsUsed)
        assertEquals(validConfigData.processing, analysisDetailData.processing)
    }

    private fun createLeafDetection(): Pair<Long, List<BoundingBoxData>> {
        val inferenceTime = 2000L
        val boundingBoxes = listOf(
            BoundingBoxData(
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
        )
        return Pair(inferenceTime, boundingBoxes)
    }

    private fun createLeafClassification(predictions: List<Pair<String, Float>>): Pair<Long, List<ClassificationData>> {
        val inferenceTime = 2000L
        val bestPrediction = Pair("bacterial_spot", 0.95f)
        val classifications = listOf(
            ClassificationData.sick(bestPrediction, predictions)
        )
        return Pair(inferenceTime, classifications)
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

}