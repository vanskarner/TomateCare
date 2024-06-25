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
    classificationData = classificationData,
    leafDetectionModel = leafDetectionModel,
    leafClassificationModel = leafClassificationModel,
    threadsUsed = threadsUsed,
    processing = processing
)

internal fun ActivityLogEntity.toData() = AnalysisDetailData(
    id = uid,
    imagePath = imagePath,
    date = date,
    detectionInferenceTimeMs = detectionInferenceTimeMs,
    classificationInferenceTimeMs = classificationInferenceTimeMs,
    note = note,
    numberDiseasesIdentified=numberDiseasesIdentified,
    listLeafBoxCoordinates = listLeafBoxCoordinates,
    classificationData = classificationData,
    leafDetectionModel = leafDetectionModel,
    leafClassificationModel = leafClassificationModel,
    threadsUsed = threadsUsed,
    processing = processing
)