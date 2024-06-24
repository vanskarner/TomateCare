package com.vanskarner.analysistracking.bussineslogic

import org.junit.Assert.*
import org.junit.Test

class UseCasesTest {

    @Test
    fun `getConfigUseCase return default config`() {
        val getConfigUseCase = GetConfigUseCase()
        val expectedMaxThreads = Runtime.getRuntime().availableProcessors()
        val actualConfig = getConfigUseCase.execute().getOrThrow()

        assertEquals(8, actualConfig.maxResults)
        assertEquals(expectedMaxThreads, actualConfig.maxThreads)
        assertEquals(2, actualConfig.processingList.size)
        assertEquals(5, actualConfig.modelList.size)
    }

}