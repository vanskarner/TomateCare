package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.LeafState
import com.vanskarner.analysistracking.SetConfigData
import com.vanskarner.diseases.DiseasesComponent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Date

class AnalyzeImageUseCaseTest {
    private lateinit var fakeDiseaseClassification: DiseaseClassification
    private lateinit var fakeRepository: Repository
    private lateinit var fakeDiseaseComponent: DiseasesComponent

    @Before
    fun setup() {
        val detections = createDetections()
        val classifications = createClassifications()
        fakeDiseaseClassification = FakeDiseaseClassification(detections, classifications)
        val analysisData = createAnalysisData()
        val analysisList = listOf(analysisData)
        fakeRepository = FakeRepository(analysisData, analysisList)
        val diseaseName = "Bacterial Spot"
        val diseaseNames = listOf("Bacterial Spot", "early Blight")
        fakeDiseaseComponent = FakeDiseaseComponent(diseaseName, diseaseNames)
    }

    @Test
    fun `AnalyzeImageUseCase with invalidConfig return InvalidConfigError`() = runTest {
        val useCase =
            AnalyzeImageUseCase(fakeDiseaseClassification, fakeRepository, fakeDiseaseComponent)
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
            AnalyzeImageUseCase(emptyDiseaseClassification, fakeRepository, fakeDiseaseComponent)
        val invalidConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "cpu",
            model = "mobileNetV2",
        )
        val exception = useCase.execute(
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            invalidConfigData
        ).exceptionOrNull()

        assertTrue(exception is AnalysisError.NoLeaves)
    }

    @Test
    fun `AnalyzeImageUseCase2 no leaves detected return NoLeavesError`() = runTest {
        val validConfigData = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "cpu",
            model = "mobileNetV2",
        )
        val useCase =
            AnalyzeImageUseCase(fakeDiseaseClassification, fakeRepository, fakeDiseaseComponent)
        val re = useCase.execute(
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            validConfigData
        ).getOrThrow()
        println("Mio $re")
    }

    private fun createPredictions() = listOf(
        Pair("bacterial_spot", 0.01f),
        Pair("early_blight", 0.95f),
        Pair("healthy", 0.01f),
        Pair("late_blight", 0.01f),
        Pair("leaf_mold", 0.01f),
        Pair("mosaic_virus", 0.01f),
        Pair("septoria_leaf_spot", 0f),
        Pair("target_spot", 0f),
        Pair("twospotted_spider_mite", 0f),
        Pair("yellow_leaf_curl_virus", 0f),
    )

    private fun createClassifications(): Pair<Long, List<ClassificationData>> {
        val inferenceTime = 2000L
        val bestPrediction = Pair("early_blight", 0.95f)
        val classifications = listOf(
            ClassificationData(LeafState.Sick, bestPrediction, createPredictions())
        )
        return Pair(inferenceTime, classifications)
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
            )
        )
        return Pair(inferenceTime, boundingBoxes)
    }

    private fun createAnalysisData(): AnalysisDetailData {
        val boundingBoxes = createDetections().second
        val classifications = createClassifications().second
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