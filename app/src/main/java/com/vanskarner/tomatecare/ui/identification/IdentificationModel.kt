package com.vanskarner.tomatecare.ui.identification

import android.graphics.Bitmap

internal data class IdentificationDetailModel(
    val id: Int,
    val creationDate: String,
    val plantImage: Bitmap,
    val leavesImage: List<LeafModel>,
    val description: String
)

internal data class LeafModel(
    val image: Bitmap,
    val isHealthy: Boolean,
    val diseases: String,
    val probability: Float
)

internal data class LeafInfoModel(
    val image: Bitmap,
    val isHealthy: Boolean,
    val prediction: String,
    val shortDescriptionDisease: String,
)

internal data class SummaryModel(
    val totalTimeSpent: String,
    val analyzedLeaves: Int,
    val identifiedDiseases: Int,
    val diseases: String,
    val recommendations: List<RecommendationModel>
)

internal data class RecommendationModel(val diseaseName: String, val diseaseControl: String)