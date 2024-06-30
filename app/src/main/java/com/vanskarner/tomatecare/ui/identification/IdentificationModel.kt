package com.vanskarner.tomatecare.ui.identification

import com.vanskarner.tomatecare.ui.common.BoundingBoxModel

internal data class IdentificationDetailModel(
    val id: Int,
    val creationDate: String,
    val imgPath: String,
    val boundingBoxes: List<BoundingBoxModel>,
    val leavesImage: List<LeafModel>
)

internal data class LeafModel(
    val keyCode:String,
    val rootImagePath: String,
    val boundingBoxModel: BoundingBoxModel,
    val isHealthy: Boolean,
    val diseases: String,
    val probability: Float
)

internal data class LeafInfoModel(
    val keyCode:String,
    val rootImagePath: String,
    val boundingBoxModel: BoundingBoxModel,
    val isHealthy: Boolean,
    val prediction: String,
    val shortDescriptionDisease: String,
)

internal data class SummaryModel(
    val detectionInference: String,
    val classificationInference: String,
    val detectionModel: String,
    val classificationModel: String,
    val usedThreads: String,
    val processing: String,
    val identifiedDiseases: String,
    val diseases: String,
    val recommendations: List<RecommendationModel>
) {
    companion object {
        fun empty() = SummaryModel(
            detectionInference = "",
            classificationInference = "",
            detectionModel = "",
            classificationModel = "",
            usedThreads = "",
            processing = "",
            identifiedDiseases = "",
            diseases = "",
            recommendations = emptyList()
        )
    }
}

internal data class RecommendationModel(val diseaseName: String, val diseaseControl: String)