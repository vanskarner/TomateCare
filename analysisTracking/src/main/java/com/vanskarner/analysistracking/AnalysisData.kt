package com.vanskarner.analysistracking

import java.util.Date

data class AnalysisData(
    val id: Int,
    val isHealthy: Boolean,
    val imagePath: String,
    val numberDiseases: Int,
    val note: String,
    val date: String,
)

data class AnalysisDetailData(
    val id: Int,
    val imagePath: String,
    val date: Date,
    val detectionInferenceTimeMs: Long,
    val classificationInferenceTimeMs: Long,
    val note: String,
    val numberDiseasesIdentified: Int,
    val listLeafBoxCoordinates: List<BoundingBox>,
    val classifications: List<Classification>,
)