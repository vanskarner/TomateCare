package com.vanskarner.tomatecare.ui.capture

internal data class SettingModel(
    var maxResults: Int,
    var threads: Int,
    val processors: List<String>,
    val models: List<String>,
    val resultsLimit: Int,
    val threadLimit: Int,
    var selectedProcessorIndex: Int,
    var selectedModelIndex: Int
)