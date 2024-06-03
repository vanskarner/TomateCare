package com.vanskarner.tomatecare.ui.activitylogs

internal data class LogModel(
    val id: Int,
    val isHealthy: Boolean,
    val imagePath: String,
    val numberDiseases: Int,
    val note: String,
    val date: String,
    var checked: Boolean
)