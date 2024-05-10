package com.vanskarner.tomatecare.capture

data class SettingModel(
    var threshold: Float,
    var maxResults: Int,
    var threads: Int,
    val processors: List<String>,
    val models: List<String>,
    val thresholdLimit: Float,
    val resultsLimit: Int,
    val threadLimit: Int,
    var selectedProcessorIndex: Int,
    var selectedModelIndex: Int,
    val thresholdIncrease: Float = 0.1f
)