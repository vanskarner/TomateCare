package com.vanskarner.analysistracking

data class AnalysisData(
    val id: Int,
    val isHealthy: Boolean,
    val imagePath: String,
    val numberDiseases: Int,
    val note: String,
    val date: String,
)