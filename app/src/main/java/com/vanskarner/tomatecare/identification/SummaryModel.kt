package com.vanskarner.tomatecare.identification

data class SummaryModel(
    val totalTimeSpent: String,
    val analyzedLeaves: Int,
    val identifiedDiseases: Int,
    val diseases: String,
    val recommendations: List<RecommendationModel>
)