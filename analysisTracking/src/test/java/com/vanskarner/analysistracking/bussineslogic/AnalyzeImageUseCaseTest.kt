package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.LeafState
import com.vanskarner.analysistracking.SetConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

class AnalyzeImageUseCaseTest {
    private lateinit var fakeDiseaseClassification: DiseaseClassification
    private lateinit var fakeRepository: Repository
    private val expectedPredictions = createPredictions()
    private val expectedClassifications = createClassifications(expectedPredictions)
    private val expectedDetections = createDetections()
    private val expectedAnalysis = createAnalysisData(expectedPredictions)

    @Before
    fun setup() {
        fakeDiseaseClassification =
            FakeDiseaseClassification(expectedDetections, expectedClassifications)
        fakeRepository =
            FakeRepository(expectedAnalysis, listOf(expectedAnalysis))
    }

    @Test
    fun `AnalyzeImageUseCase with invalidConfig return InvalidConfigError`() = runTest {
        val useCase =
            AnalyzeImageUseCase(fakeDiseaseClassification, fakeRepository)
        val invalidConfigData = SetConfigData(
            numberResults = 0,
            numberThreads = 0,
            processing = "unknown",
            model = "unknown",
        )
        val exception = useCase.execute(
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            invalidConfigData
        ).exceptionOrNull()

        assertTrue(exception is AnalysisError.InvalidConfig)
    }

    @Test
    fun `AnalyzeImageUseCase no leaves detected return NoLeavesError`() = runTest {
        val emptyDetections = Pair(2000L, emptyList<BoundingBoxData>())
        val emptyClassifications = Pair(2000L, emptyList<ClassificationData>())
        val emptyDiseaseClassification =
            FakeDiseaseClassification(emptyDetections, emptyClassifications)
        val useCase =
            AnalyzeImageUseCase(emptyDiseaseClassification, fakeRepository)
        val invalidConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2",
        )
        val exception = useCase.execute(
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            invalidConfigData
        ).exceptionOrNull()

        assertTrue(exception is AnalysisError.NoLeaves)
    }

    @Test
    fun `AnalyzeImageUseCase with dataOK savedAnalysis`() = runTest {
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "CPU",
            model = "MobileNetV2",
        )
        val useCase =
            AnalyzeImageUseCase(fakeDiseaseClassification, fakeRepository)
        val expectedPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        val analysisDetailData = useCase.execute(expectedPath, validConfigData).getOrThrow()

        assertEquals(1, analysisDetailData.id)
        assertEquals(expectedPath, analysisDetailData.imagePath)
        assertEquals(expectedDetections.first, analysisDetailData.detectionInferenceTimeMs)
        assertEquals(
            expectedClassifications.first,
            analysisDetailData.classificationInferenceTimeMs
        )
        assertEquals("", analysisDetailData.note)
        assertEquals(
            expectedClassifications.second.distinct().size,
            analysisDetailData.numberDiseasesIdentified
        )
        assertEquals(expectedDetections.second.size, analysisDetailData.listLeafBoxCoordinates.size)
        assertEquals(
            expectedClassifications.second.size,
            analysisDetailData.classificationData.size
        )
        assertEquals(
            expectedClassifications.second,
            analysisDetailData.classificationData
        )
        assertEquals(
            expectedPredictions,
            analysisDetailData.classificationData[0].predictions
        )
        assertEquals(
            expectedPredictions,
            analysisDetailData.classificationData[1].predictions
        )
    }

    private fun createDetections(): Pair<Long, List<BoundingBoxData>> {
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
            ),
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

    private fun createClassifications(predictions: List<Pair<String, Float>>): Pair<Long, List<ClassificationData>> {
        val inferenceTime = 2000L
        val bestPrediction = Pair("bacterial_spot", 0.95f)
        val classifications = listOf(
            ClassificationData(LeafState.Sick, bestPrediction, predictions),
            ClassificationData(LeafState.Sick, bestPrediction, predictions)
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

    private fun createAnalysisData(predictions: List<Pair<String, Float>>): AnalysisDetailData {
        val boundingBoxes = createDetections().second
        val classifications = createClassifications(predictions).second
        return AnalysisDetailData(
            id = 1,
            imagePath = "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            date = Date(),
            detectionInferenceTimeMs = 2000,
            classificationInferenceTimeMs = 3000,
            note = "Some note",
            numberDiseasesIdentified = 2,
            listLeafBoxCoordinates = boundingBoxes,
            classificationData = classifications
        )
    }

}