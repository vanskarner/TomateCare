package com.vanskarner.analysistracking.persistence

import com.vanskarner.analysistracking.AnalysisDetailData

internal fun AnalysisDetailData.toEntity() = ActivityLogEntity(
    imagePath = imagePath,
    date = date,
    detectionInferenceTimeMs = detectionInferenceTimeMs,
    classificationInferenceTimeMs = classificationInferenceTimeMs,
    note = note,
    numberDiseasesIdentified = numberDiseasesIdentified,
    listLeafBoxCoordinates = listLeafBoxCoordinates,
    classifications = classifications
)

internal fun ActivityLogEntity.toData() = AnalysisDetailData(
    uid,
    imagePath,
    date,
    detectionInferenceTimeMs,
    classificationInferenceTimeMs,
    note,
    numberDiseasesIdentified,
    listLeafBoxCoordinates,
    classifications
)