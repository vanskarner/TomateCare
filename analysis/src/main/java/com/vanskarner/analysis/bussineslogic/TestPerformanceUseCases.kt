package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.SetConfigData
import com.vanskarner.analysis.TestConfigData

internal class TestPerformanceUseCase(
    private val computerVisionPerformance: ComputerVisionPerformance,
    private val validateConfigUseCase: ValidateConfigUseCase
) {

    suspend fun execute(testConfigData: TestConfigData) = runCatching {
        val unvalidatedConfig = SetConfigData(
            numberResults = 8,
            numberThreads = testConfigData.numberThreads,
            processing = testConfigData.processing,
            model = testConfigData.model
        )
        val validConfig = validateConfigUseCase.execute(unvalidatedConfig).getOrThrow()
        val useGPU = validConfig.processing == "GPU"
        val detectionInferenceTime = computerVisionPerformance
            .testYoloV8nModel(useGPU, validConfig.numberThreads)
            .getOrThrow()
        val classificationInferenceTime = when (validConfig.model) {
            "MobileNetV3Small" -> computerVisionPerformance.testMobileNetV3SmallModel(
                useGPU,
                validConfig.numberThreads
            )

            "MobileNetV3Large" -> computerVisionPerformance.testMobileNetV3LargeModel(
                useGPU,
                validConfig.numberThreads
            )

            "MobileNetV2" -> computerVisionPerformance.testMobileNetV2Model(
                useGPU,
                validConfig.numberThreads
            )

            "SqueezeNetMish" -> computerVisionPerformance.testSqueezeNetMishModel(
                useGPU,
                validConfig.numberThreads
            )

            "NASNetMobile" -> computerVisionPerformance.testNASNetMobileModel(
                useGPU,
                validConfig.numberThreads
            )

            else -> Result.failure(AnalysisError.InvalidModel)
        }
        Pair(detectionInferenceTime, classificationInferenceTime.getOrThrow())
    }
}

internal class GetTestResourcesUseCase(
    private val computerVisionPerformance: ComputerVisionPerformance
) {

    suspend fun execute() = runCatching {
        val detectionInputStream = computerVisionPerformance.imageUsedForDetection()
        val classificationInputStream = computerVisionPerformance.imagesUsedForClassification()
        Pair(detectionInputStream, classificationInputStream)
    }

}