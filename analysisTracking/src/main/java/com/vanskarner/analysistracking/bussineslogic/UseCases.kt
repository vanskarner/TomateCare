package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.ConfigData

internal class GetAnalysisTrackingUseCases(private val repository: Repository) {

    suspend fun execute() = repository.list()

}

internal class GetConfigParams() {
    companion object {
        private const val MAX_RESULTS = 8
        private const val MAX_THREADS = 8
        private val PROCESSING_LIST = listOf("CPU", "GPU")
        private val MODEL_LIST = listOf(
            "MobileNetV3Small",
            "MobileNetV3Large",
            "MobileNetV2",
            "SqueezeNetMish",
            "NASNetMobile",
        )
    }

    fun execute() {
        val config = ConfigData(
            MAX_RESULTS, MAX_THREADS, listOf(), listOf()
        )
    }

}