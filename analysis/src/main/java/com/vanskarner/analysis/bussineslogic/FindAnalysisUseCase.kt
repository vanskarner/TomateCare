package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.LeafState
import com.vanskarner.diseases.DiseasesComponent

internal class FindAnalysisUseCase(
    private val repository: Repository,
    private val diseasesComponent: DiseasesComponent
) {

    suspend fun execute(id: Int): Result<AnalysisDetailData> = runCatching {
        val result = repository.findAnalysis(id).getOrThrow()
        val formattedResult = result.copy(
            diseaseKeyCodes = getDiseaseKeyCodes(result.classificationData),
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

    private fun getDiseaseKeyCodes(classification: List<ClassificationData>): List<String> {
        val haveAnyDisease = classification.any { it.leafState == LeafState.Sick }
        var diseaseKeyCodes = emptyList<String>()
        if (haveAnyDisease) {
            diseaseKeyCodes = classification
                .map { it.bestPrediction.first }
                .distinct()
        }
        return diseaseKeyCodes
    }

}