package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.ConfigData
import com.vanskarner.analysistracking.SetConfigData
import com.vanskarner.diseases.DiseasesComponent
import java.util.Date

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
        val isValidProcessing = configParams.processingList
            .map { it.lowercase() }
            .contains(setConfigData.processing.lowercase())
        val isValidModel = configParams.modelList
            .map { it.lowercase() }
            .contains(setConfigData.model.lowercase())
        return if (isValidNumberResults && isValidNumberThreads && isValidProcessing && isValidModel)
            Result.success(setConfigData)
        else Result.failure(AnalysisError.InvalidConfig)
    }

}

internal class AnalyzeImageUseCase(
    private val diseaseClassification: DiseaseClassification,
    private val repository: Repository,
    private val diseasesComponent: DiseasesComponent
) {

    suspend fun execute(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData> = runCatching {
        ValidateConfigUseCase().execute(configData).getOrThrow()
        val leafDetections = diseaseClassification.detectLeaves(imgPath)
        val detectionInferenceTime = leafDetections.first
        val boundingBoxes = leafDetections.second
        if (boundingBoxes.isEmpty()) return Result.failure(AnalysisError.NoLeaves)
        val classificationResults =
            getClassifications(imgPath, boundingBoxes, configData).getOrThrow()
        val classificationInferenceTime = classificationResults.first
        val classifications = classificationResults.second
        val formattedClassifications = classifications.map { item ->
            val bestPredictionCode = item.bestPrediction.first
            val realName = diseasesComponent.getNameByKeyCode(bestPredictionCode).getOrThrow()
            val bestFormattedPrediction = Pair(realName, item.bestPrediction.second)
            val predictionNames = item.predictions.map { it.first }
            val realNames = diseasesComponent.getNamesByKeyCodes(predictionNames).getOrThrow()
            val formattedPredictions = realNames.zip(item.predictions) { label, prediction ->
                Pair(label, prediction.second)
            }
            ClassificationData(
                leafState = item.leafState,
                bestPrediction = bestFormattedPrediction,
                predictions = formattedPredictions
            )
        }
        val numberDiseasesIdentified = formattedClassifications
            .map { it.bestPrediction.first }
            .distinct().size
        val analysisData = AnalysisDetailData(
            1,//*
            imagePath = imgPath,
            date = Date(),
            detectionInferenceTimeMs = detectionInferenceTime,
            classificationInferenceTimeMs = classificationInferenceTime,
            note = "",//*
            numberDiseasesIdentified = numberDiseasesIdentified,
            listLeafBoxCoordinates = boundingBoxes,
            classificationData = formattedClassifications
        )
        repository.saveAnalysis(analysisData).getOrThrow()
        return Result.success(analysisData)
    }

    private suspend fun getClassifications(
        imgPath: String,
        boundingBoxes: List<BoundingBoxData>,
        config: SetConfigData
    ): Result<Pair<Long, List<ClassificationData>>> {
        val useGPU = config.model.lowercase() == "gpu"
        return when (config.model.lowercase()) {
            "MobileNetV3Small" -> diseaseClassification.classifyLeavesWithMobileNetV3Small(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )

            "MobileNetV3Large" -> diseaseClassification.classifyLeavesWithMobileNetV3Large(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )

            "MobileNetV2" -> diseaseClassification.classifyLeavesWithMobileNetV2(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )

            "SqueezeNetMish" -> diseaseClassification.classifyLeavesWithSqueezeNetMish(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )

            "NASNetMobile" -> diseaseClassification.classifyLeavesWithNASNetMobile(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )

            else -> diseaseClassification.classifyLeavesWithMobileNetV3Small(
                imgPath,
                boundingBoxes,
                useGPU,
                config.numberThreads
            )
        }
    }

}