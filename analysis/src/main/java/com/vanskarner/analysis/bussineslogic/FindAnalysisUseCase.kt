package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.diseases.DiseasesComponent

internal class FindAnalysisUseCase(
    private val repository: Repository,
    private val diseasesComponent: DiseasesComponent
) {

    suspend fun execute(id: Int): Result<AnalysisDetailData> = runCatching {
        val result = repository.findAnalysis(id).getOrThrow()
        val formattedResult = result.copy(
            classificationData = result.classificationData.map { item ->
                val keyCode = item.bestPrediction
                val keyCodes = item.predictions.map { prediction -> prediction.first }
                val realName = diseasesComponent.getNameByKeyCode(keyCode.first).getOrThrow()
                val realNames = diseasesComponent.getNamesByKeyCodes(keyCodes).getOrThrow()
                item.copy(
                    bestPrediction = Pair(realName, item.bestPrediction.second),
                    predictions = item.predictions.zip(realNames) { v1, v2 -> v1.copy(first = v2) }
                )
            }
        )
        return Result.success(formattedResult)
    }

}