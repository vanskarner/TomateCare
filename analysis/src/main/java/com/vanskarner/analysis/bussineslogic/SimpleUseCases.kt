package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.ConfigData
import com.vanskarner.analysis.SetConfigData

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

    fun execute(): ConfigData {
        val maxThreads = Runtime.getRuntime().availableProcessors()
        return ConfigData(MAX_RESULTS, maxThreads, PROCESSING_LIST, MODEL_LIST)
    }

}

internal class ValidateConfigUseCase {

    fun execute(setConfigData: SetConfigData): Result<SetConfigData> {
        val configParams = GetConfigUseCase().execute()
        val isValidNumberResults = setConfigData.numberResults in 1..configParams.maxResults
        val isValidNumberThreads = setConfigData.numberThreads in 1..configParams.maxThreads
        val isValidProcessing = configParams.processingList.contains(setConfigData.processing)
        val isValidModel = configParams.modelList.contains(setConfigData.model)
        return if (isValidNumberResults && isValidNumberThreads && isValidProcessing && isValidModel)
            Result.success(setConfigData)
        else Result.failure(AnalysisError.InvalidConfig)
    }

}

internal class UpdateAnalysisNoteUseCase(
    private val repository: Repository
) {

    suspend fun execute(id: Int, note: String) = repository.updateAnalysisNote(id, note)

}

internal class DeleteAnalysisUseCase(
    private val repository: Repository
) {

    suspend fun execute(id: List<Int>) = repository.deleteAnalysis(id)

}

internal class GetAnalysisNoteUseCase(
    private val repository: Repository
) {

    suspend fun execute(id: Int) = repository.findAnalysisNote(id)

}