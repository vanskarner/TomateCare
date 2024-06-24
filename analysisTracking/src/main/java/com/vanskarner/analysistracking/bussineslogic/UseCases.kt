package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.ConfigData

internal class GetAnalysisTrackingUseCases(private val repository: Repository) {

    suspend fun execute() = repository.list()

}

internal class GetConfigUseCase {
    companion object {
        private const val MAX_RESULTS = 8
        private val PROCESSING_LIST = listOf("CPU", "GPU")
        private val MODEL_LIST = listOf(
            "MobileNetV3Small",
            "MobileNetV3Large",
            "MobileNetV2",
            "SqueezeNetMish",
            "NASNetMobile",
        )
    }

    fun execute(): Result<ConfigData> {
        val maxThreads = Runtime.getRuntime().availableProcessors()
        val config = ConfigData(MAX_RESULTS, maxThreads, PROCESSING_LIST, MODEL_LIST)
        return Result.success(config)
    }

}