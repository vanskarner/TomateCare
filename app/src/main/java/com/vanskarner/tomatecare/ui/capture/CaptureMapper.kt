package com.vanskarner.tomatecare.ui.capture

import com.vanskarner.analysis.ConfigData
import com.vanskarner.analysis.SetConfigData

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

internal fun SettingModel.toData() = SetConfigData(
    numberThreads = threads,
    numberResults = maxResults,
    processing = processors[selectedProcessorIndex],
    model = models[selectedModelIndex]
)