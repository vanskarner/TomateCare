package com.vanskarner.tomatecare.ui.capture

import com.vanskarner.analysis.ConfigData

internal fun ConfigData.toModel() = SettingModel(
    maxResults = maxResults,
    threads = maxThreads,
    processors = processingList,
    models = modelList,
    resultsLimit = maxResults,
    threadLimit = maxThreads,
    selectedModelIndex = modelList.lastIndex,
    selectedProcessorIndex = processingList.lastIndex
)