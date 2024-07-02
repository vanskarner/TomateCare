package com.vanskarner.tomatecare.ui.identification

import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.LeafState
import com.vanskarner.tomatecare.ui.common.BoundingBoxModel
import com.vanskarner.tomatecare.ui.common.toModel

internal fun ClassificationData.toLeafModel(
    rootImagePath: String,
    boundingBoxModel: BoundingBoxModel
) = LeafModel(
    keyCode = bestPredictionKeyCode,
    rootImagePath = rootImagePath,
    boundingBoxModel = boundingBoxModel,
    diseases = bestPrediction.first,
    isHealthy = leafState == LeafState.Healthy,
    probability = bestPrediction.second
)

internal fun AnalysisDetailData.toIdentificationDetailModel(
    creationDate: String,
    leafModelList: List<LeafModel>
) =
    IdentificationDetailModel(
        id = id,
        creationDate = creationDate,
        imgPath = imagePath,
        boundingBoxes = listLeafBoxCoordinates.toModel(),
        leavesImage = leafModelList
    )

internal fun AnalysisDetailData.toSummaryModel(recommendations: List<RecommendationModel>) =
    SummaryModel(
        detectionInference = "$detectionInferenceTimeMs",
        classificationInference = "$classificationInferenceTimeMs",
        detectionModel = leafDetectionModel,
        classificationModel = leafClassificationModel,
        usedThreads = threadsUsed,
        processing = processing,
        totalLeavesAnalyzed = listLeafBoxCoordinates.size.toString(),
        identifiedDiseases = "$numberDiseasesIdentified",
        diseases = recommendations.joinToString(",") { item -> item.diseaseName },
        recommendations = recommendations
    )

internal fun LeafModel.toLeafInfoModel(symptoms: String) = LeafInfoModel(
    keyCode = keyCode,
    rootImagePath = rootImagePath,
    boundingBoxModel = boundingBoxModel,
    isHealthy = isHealthy,
    prediction = "$diseases (${probability * 100}%)",
    shortDescriptionDisease = symptoms,
)