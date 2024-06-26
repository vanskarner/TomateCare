package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.LeafState
import com.vanskarner.analysistracking.SetConfigData
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
            val leafClassification =
                classifyLeavesUseCase.execute(imgPath, leafDetection.second, validConfig)
                    .getOrThrow()
            val analysisData = AnalysisDetailData(
                imagePath = imgPath,
                date = Date(),
                detectionInferenceTimeMs = leafDetection.first,
                classificationInferenceTimeMs = leafClassification.first,
                numberDiseasesIdentified = getNumberDiseases(leafClassification.second),
                listLeafBoxCoordinates = leafDetection.second,
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