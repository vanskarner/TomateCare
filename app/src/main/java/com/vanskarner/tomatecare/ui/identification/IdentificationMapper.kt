package com.vanskarner.tomatecare.ui.identification

import android.graphics.Bitmap
import com.vanskarner.analysis.AnalysisDetailData
import com.vanskarner.analysis.ClassificationData
import com.vanskarner.analysis.LeafState
import com.vanskarner.tomatecare.ui.common.toModel

internal fun ClassificationData.toLeafModel(leafImage: Bitmap) = LeafModel(
    keyCode = bestPredictionKeyCode,
    image = leafImage,
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
        identifiedDiseases = "$numberDiseasesIdentified",
        diseases = recommendations
            .map { item -> item.diseaseName }
            .mapIndexed { index, item ->
                if (index % 2 == 1) "- $item\n"
                else "- $item "
            }.joinToString(""),
        recommendations = recommendations
    )

internal fun LeafModel.toLeafInfoModel(symptoms: String) = LeafInfoModel(
    keyCode = keyCode,
    image = image,
    isHealthy = isHealthy,
    prediction = "$diseases (${probability * 100}%)",
    shortDescriptionDisease = symptoms,
)