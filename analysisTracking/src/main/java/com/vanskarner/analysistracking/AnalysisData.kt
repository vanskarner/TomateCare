package com.vanskarner.analysistracking

data class AnalysisData(
    val id: Int,
    val isSick: Boolean,
    val imagePath: String,
    val note: String,
    val date: String,
)