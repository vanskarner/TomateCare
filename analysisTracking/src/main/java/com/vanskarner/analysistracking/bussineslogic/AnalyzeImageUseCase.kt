package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.SetConfigData
import java.util.Date

internal class AnalyzeImageUseCase(
    private val diseaseClassification: DiseaseClassification,
    private val repository: Repository
) {

    suspend fun execute(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData> =
        runCatching {
            ValidateConfigUseCase().execute(configData).getOrThrow()
            val leafDetections = diseaseClassification.detectLeaves(imgPath)
            val detectionInferenceTime = leafDetections.first
            val boundingBoxes = leafDetections.second
            if (boundingBoxes.isEmpty()) return Result.failure(AnalysisError.NoLeaves)
            val classificationResults =
                getClassifications(imgPath, boundingBoxes, configData).getOrThrow()
            val classificationInferenceTime = classificationResults.first
            val classifications = classificationResults.second
            val numberDiseasesIdentified = classifications
                .map { it.bestPrediction.first }
                .distinct().size
            val analysisData = AnalysisDetailData(
                imagePath = imgPath,
                date = Date(),
                detectionInferenceTimeMs = detectionInferenceTime,
                classificationInferenceTimeMs = classificationInferenceTime,
                numberDiseasesIdentified = numberDiseasesIdentified,
                listLeafBoxCoordinates = boundingBoxes,
                classificationData = classifications
            )
            val savedId = repository.saveAnalysis(analysisData).getOrThrow()
            return Result.success(analysisData.copy(id = savedId))
        }

    private suspend fun getClassifications(
        imgPath: String,
        boundingBoxes: List<BoundingBoxData>,
        config: SetConfigData
    ): Result<Pair<Long, List<ClassificationData>>> {
        val useGPU = config.processing == "GPU"
        return when (config.model) {
            "MobileNetV3Small" -> {
                diseaseClassification.classifyLeavesWithMobileNetV3Small(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }

            "MobileNetV3Large" -> {
                diseaseClassification.classifyLeavesWithMobileNetV3Large(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }

            "MobileNetV2" -> {
                diseaseClassification.classifyLeavesWithMobileNetV2(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }

            "SqueezeNetMish" -> {
                diseaseClassification.classifyLeavesWithSqueezeNetMish(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }

            "NASNetMobile" -> {
                diseaseClassification.classifyLeavesWithNASNetMobile(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }

            else -> {
                diseaseClassification.classifyLeavesWithMobileNetV3Small(
                    imgPath,
                    boundingBoxes,
                    useGPU,
                    config.numberThreads
                )
            }
        }
    }

}