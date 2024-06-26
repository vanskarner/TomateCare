package com.vanskarner.tomatecare.ui.activitylogs

import com.vanskarner.analysistracking.AnalysisData

internal fun AnalysisData.toModel(date:String) = LogModel(id, isHealthy, imagePath, numberDiseases, note, date, false)
