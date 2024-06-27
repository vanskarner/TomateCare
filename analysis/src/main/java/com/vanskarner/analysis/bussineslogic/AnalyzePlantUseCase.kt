package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.LeafState
import com.vanskarner.analysis.SetConfigData
import java.util.Date

internal class AnalyzePlantUseCase(
    private val validateConfigUseCase: ValidateConfigUseCase,
    private val detectLeavesUseCase: DetectLeavesUseCase,
    private val classifyLeavesUseCase: ClassifyLeavesUseCase,
    private val repository: Repository
) {

    suspend fun execute(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData> =
        runCatching {
            val validConfig = validateConfigUseCase.execute(configData).getOrThrow()
            val leafDetection = detectLeavesUseCase.execute(imgPath).getOrThrow()
            val leafDetectionSelection = leafDetection.copy(
                second = leafDetection.second.sortedByDescending { it.cnf }
                    .take(validConfig.numberResults)
            )
            val leafClassification =
                classifyLeavesUseCase.execute(imgPath, leafDetectionSelection.second, validConfig)
                    .getOrThrow()
            val analysisData = AnalysisDetailData(
                imagePath = imgPath,
                date = Date(),
                detectionInferenceTimeMs = leafDetectionSelection.first,
                classificationInferenceTimeMs = leafClassification.first,
                numberDiseasesIdentified = getNumberDiseases(leafClassification.second),
                listLeafBoxCoordinates = leafDetectionSelection.second,
                classificationData = leafClassification.second,
                leafDetectionModel = "YoloV8",
                leafClassificationModel = validConfig.model,
                threadsUsed = validConfig.numberThreads.toString(),
                processing = validConfig.processing
            )
            val savedId = repository.saveAnalysis(analysisData).getOrThrow()
            return Result.success(analysisData.copy(id = savedId))
        }

    private fun getNumberDiseases(classification: List<ClassificationData>): Int {
        val haveAnyDisease = classification.any { it.leafState == LeafState.Sick }
        var numberDiseasesIdentified = 0
        if (haveAnyDisease) {
            numberDiseasesIdentified = classification
                .map { it.bestPrediction.first }
                .distinct().size
        }
        return numberDiseasesIdentified
    }

}