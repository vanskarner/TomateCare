package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.SetConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class SimpleUseCasesTest {

    @Test
    fun `GetConfigUseCase return default config`() {
        val useCase = GetConfigUseCase()
        val expectedMaxThreads = Runtime.getRuntime().availableProcessors()
        val actualConfig = useCase.execute()

        assertEquals(8, actualConfig.maxResults)
        assertEquals(expectedMaxThreads, actualConfig.maxThreads)
        assertEquals(2, actualConfig.processingList.size)
        assertEquals(5, actualConfig.modelList.size)
    }

    @Test
    fun `ValidateConfigUseCase with invalidConfig return InvalidConfigError`() {
        val useCase = ValidateConfigUseCase()
        val invalidNumberResults = SetConfigData(
            numberResults = 0,
            numberThreads = 4,
            processing = "GPU",
            model = "MobileNetV2",
        )
        val invalidNumberThreads = SetConfigData(
            numberResults = 4,
            numberThreads = 0,
            processing = "GPU",
            model = "MobileNetV2",
        )
        val invalidProcessing = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "unknown",
            model = "MobileNetV2",
        )
        val invalidModel = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "GPU",
            model = "unknown",
        )
        val exception1 = useCase.execute(invalidNumberResults).exceptionOrNull()
        val exception2 = useCase.execute(invalidNumberThreads).exceptionOrNull()
        val exception3 = useCase.execute(invalidProcessing).exceptionOrNull()
        val exception4 = useCase.execute(invalidModel).exceptionOrNull()

        assertTrue(exception1 is AnalysisError.InvalidConfig)
        assertTrue(exception2 is AnalysisError.InvalidConfig)
        assertTrue(exception3 is AnalysisError.InvalidConfig)
        assertTrue(exception4 is AnalysisError.InvalidConfig)
    }

    @Test
    fun `ValidateConfigUseCase with validConfig return config`() {
        val useCase = ValidateConfigUseCase()
        val expectedConfig = SetConfigData(
            numberResults = 4,
            numberThreads = Runtime.getRuntime().availableProcessors(),
            processing = "CPU",
            model = "MobileNetV2",
        )
        val actualConfig = useCase.execute(expectedConfig).getOrThrow()

        assertEquals(expectedConfig.numberResults, actualConfig.numberResults)
        assertEquals(expectedConfig.numberThreads, actualConfig.numberThreads)
        assertEquals(expectedConfig.processing, actualConfig.processing)
        assertEquals(expectedConfig.model, actualConfig.model)
    }

    @Test
    fun `UpdateAnalysisNoteUseCase with validId should be updated`() = runTest {
        val repository: Repository = mock()
        val expectedId = 1
        val expectedNote = "Some Note"
        `when`(repository.updateAnalysisNote(expectedId, expectedNote))
            .thenReturn(Result.success(Unit))
        val useCase = UpdateAnalysisNoteUseCase(repository)
        useCase.execute(expectedId, expectedNote).getOrThrow()

        verify(repository, times(1))
            .updateAnalysisNote(expectedId, expectedNote)
    }

    @Test
    fun `DeleteAnalysisUseCase with validIds should be removed`() = runTest {
        val repository: Repository = mock()
        val expectedIds = listOf(1, 2, 3)
        `when`(repository.deleteAnalysis(expectedIds))
            .thenReturn(Result.success(expectedIds.size))
        val useCase = DeleteAnalysisUseCase(repository)
        val actualDeletedItems = useCase.execute(expectedIds).getOrThrow()

        verify(repository, times(1))
            .deleteAnalysis(expectedIds)
        assertEquals(expectedIds.size, actualDeletedItems)
    }

}