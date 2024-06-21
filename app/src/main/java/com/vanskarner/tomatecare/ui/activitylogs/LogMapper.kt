package com.vanskarner.tomatecare.ui.activitylogs

import com.vanskarner.analysistracking.AnalysisData

internal fun List<AnalysisData>.toListModel() = this.map { it.toModel() }

internal fun AnalysisData.toModel() = LogModel(id, isHealthy, imagePath, numberDiseases, note, date, false)
