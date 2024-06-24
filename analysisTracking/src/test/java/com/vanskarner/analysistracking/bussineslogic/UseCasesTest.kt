package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.SetConfigData
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
        val invalidConfigData = SetConfigData(
            numberResults = 0,
            numberThreads = 0,
            processing = "unknown",
            model = "unknown",
        )
        val actualConfig = useCase.execute(invalidConfigData).exceptionOrNull()

        assertTrue(actualConfig is AnalysisError.InvalidConfig)
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