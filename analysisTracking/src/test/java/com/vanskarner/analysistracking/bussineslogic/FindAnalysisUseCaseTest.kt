package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.LeafState
import com.vanskarner.diseases.DiseasesComponent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date

class FindAnalysisUseCaseTest {

    @Test
    fun `execute with validId return Formatted AnalysisDetailData`() = runTest {
        val repository: Repository = mock()
        val diseasesComponent: DiseasesComponent = mock()
        val expected = createAnalysisDetailData()
        val keyCode = expected.classificationData[0].bestPrediction.first
        val keyCodes = expected.classificationData[0].predictions.map { prediction -> prediction.first }
        val expectedRealName = "Bacterial Spot"
        val expectedRealNames = listOf(
            "Bacterial Spot",
            "Early Blight",
            "Healthy",
            "Late Blight",
            "Leaf Mold",
            "Mosaic Virus",
            "Septoria Leaf Spot",
            "Target Spot",
            "Twospotted Spider Smite",
            "Yellow Leaf Curl Virus"
        )
        `when`(repository.findAnalysis(1)).thenReturn(Result.success(expected))

        `when`(diseasesComponent.getNameByKeyCode(keyCode))
            .thenReturn(Result.success(expectedRealName))
        `when`(diseasesComponent.getNamesByKeyCodes(keyCodes))
            .thenReturn(Result.success(expectedRealNames))
        val useCase = FindAnalysisUseCase(repository, diseasesComponent)
        val actual = useCase.execute(1).getOrThrow()

        verify(repository, times(1)).findAnalysis(1)
        verify(diseasesComponent, times(1)).getNameByKeyCode(keyCode)
        verify(diseasesComponent, times(1)).getNamesByKeyCodes(keyCodes)
        assertEquals(expected.id,actual.id)
        assertEquals(expected.imagePath,actual.imagePath)
        assertEquals(expected.date,actual.date)
        assertEquals(expected.detectionInferenceTimeMs,actual.detectionInferenceTimeMs)
        assertEquals(expected.classificationInferenceTimeMs,actual.classificationInferenceTimeMs)
        assertEquals(expected.note,actual.note)
        assertEquals(expected.numberDiseasesIdentified,actual.numberDiseasesIdentified)
        assertEquals(expected.listLeafBoxCoordinates,actual.listLeafBoxCoordinates)
        assertEquals(expectedRealName,actual.classificationData[0].bestPrediction.first)
        assertEquals(expectedRealNames,actual.classificationData[0].predictions.map { prediction -> prediction.first })
        assertEquals(expected.leafDetectionModel,actual.leafDetectionModel)
        assertEquals(expected.leafClassificationModel,actual.leafClassificationModel)
        assertEquals(expected.threadsUsed,actual.threadsUsed)
        assertEquals(expected.processing,actual.processing)
    }

    private fun createAnalysisDetailData(): AnalysisDetailData {
        val listLeafBoxCoordinates = listOf(
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
        val bestPrediction = Pair("bacterial_spot", 0.95f)
        val predictions = listOf(
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
        val classifications = listOf(
            ClassificationData(LeafState.Sick, bestPrediction, predictions)
        )
        return AnalysisDetailData(
            id = 1,
            imagePath = "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg",
            date = Date(),
            detectionInferenceTimeMs = 2000,
            classificationInferenceTimeMs = 3000,
            note = "Some note",
            numberDiseasesIdentified = 1,
            listLeafBoxCoordinates = listLeafBoxCoordinates,
            classificationData = classifications,
            leafDetectionModel = "YoloV8",
            leafClassificationModel = "MobileNetV2",
            threadsUsed = "4",
            processing = "CPU",
        )
    }

}