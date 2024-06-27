package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.LeafState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date

class GetAnalysisUseCaseTest {

    @Test
    fun `execute but there are no items return empty list`() = runTest {
        val repository: Repository = mock()
        val result = Result.success(emptyList<AnalysisDetailData>())
        `when`(repository.getAnalysisList()).thenReturn(result)
        val actualList = GetAnalysisUseCase(repository).execute().getOrThrow()

        verify(repository, times(1)).getAnalysisList()
        assertTrue(actualList.isEmpty())
    }

    @Test
    fun `execute there is an item return list`() = runTest {
        val repository: Repository = mock()
        val expectedList = listOf(createAnalysisData())
        val result = Result.success(expectedList)
        `when`(repository.getAnalysisList()).thenReturn(result)
        val actualList = GetAnalysisUseCase(repository).execute().getOrThrow()

        verify(repository, times(1)).getAnalysisList()
        assertEquals(expectedList.size, actualList.size)
        assertEquals(expectedList[0].numberDiseasesIdentified == 0, actualList[0].isHealthy)
        assertEquals(expectedList[0].imagePath, actualList[0].imagePath)
        assertEquals(expectedList[0].numberDiseasesIdentified, actualList[0].numberDiseases)
        assertEquals(expectedList[0].note, actualList[0].note)
        assertEquals(expectedList[0].date, actualList[0].date)
    }

    private fun createAnalysisData(): AnalysisDetailData {
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