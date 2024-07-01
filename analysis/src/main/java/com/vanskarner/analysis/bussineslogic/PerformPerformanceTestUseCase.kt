package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.SetConfigData
import com.vanskarner.analysis.TestConfigData

internal class PerformPerformanceTestUseCase(
    private val computerVision: ComputerVision,
    private val validateConfigUseCase: ValidateConfigUseCase
) {

    fun execute(testConfigData: TestConfigData) {
//        computerVision.checkGpuCompatibility()
        val unvalidatedConfig = SetConfigData(
            numberResults = 8,
            numberThreads = testConfigData.numberThreads,
            processing = testConfigData.processing,
            model = testConfigData.model
        )
        val configValid = validateConfigUseCase.execute(unvalidatedConfig).getOrThrow()

    }
}