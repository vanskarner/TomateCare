package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.SetConfigData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class UseCasesTest {

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
            processing = "gpu",
            model = "mobileNetV2",
        )
        val invalidNumberThreads = SetConfigData(
            numberResults = 4,
            numberThreads = 0,
            processing = "gpu",
            model = "mobileNetV2",
        )
        val invalidProcessing = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "unknown",
            model = "mobileNetV2",
        )
        val invalidModel = SetConfigData(
            numberResults = 4,
            numberThreads = 4,
            processing = "gpu",
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
            processing = "cpu",
            model = "mobileNetV2",
        )
        val actualConfig = useCase.execute(expectedConfig).getOrThrow()

        assertEquals(expectedConfig.numberResults, actualConfig.numberResults)
        assertEquals(expectedConfig.numberThreads, actualConfig.numberThreads)
        assertEquals(expectedConfig.processing, actualConfig.processing)
        assertEquals(expectedConfig.model, actualConfig.model)
    }

}