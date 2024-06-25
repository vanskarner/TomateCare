package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.Mockito.`when`

class DetectLeavesUseCaseTest {

    @Test
    fun `execute but there are no leaves return NoLeavesError`() = runTest {
        val computerVision: ComputerVision = mock()
        val expectedNoLeaves = Pair(2000L, emptyList<BoundingBoxData>())
        val imgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        `when`(computerVision.detectLeaves(imgPath)).thenReturn(expectedNoLeaves)
        val exception = DetectLeavesUseCase(computerVision)
            .execute(imgPath)
            .exceptionOrNull()

        assertTrue(exception is AnalysisError.NoLeaves)
        verify(computerVision, times(1)).detectLeaves(imgPath)
    }

    @Test
    fun `execute leaves are detected return result`() = runTest {
        val computerVision: ComputerVision = mock()
        val expected = Pair(2000L, listOf(createBoundingBoxData(), createBoundingBoxData()))
        val imgPath =
            "/Android/data/com.vanskarner.tomatecare/files/Pictures/Plant_7809504466231131920.jpg"
        `when`(computerVision.detectLeaves(imgPath)).thenReturn(expected)
        val actual = DetectLeavesUseCase(computerVision)
            .execute(imgPath)
            .getOrThrow()

        assertEquals(expected.first, actual.first)
        assertEquals(expected.second, actual.second)
        verify(computerVision, times(1)).detectLeaves(imgPath)
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

}