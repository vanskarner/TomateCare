package com.vanskarner.analysistracking.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "activity_log")
data class ActivityLogEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "detection_inference_time") val detectionInferenceTimeMs: Long,
    @ColumnInfo(name = "classification_inference_time") val classificationInferenceTimeMs: Long,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "number_diseases_identified") val numberDiseasesIdentified: Int
)