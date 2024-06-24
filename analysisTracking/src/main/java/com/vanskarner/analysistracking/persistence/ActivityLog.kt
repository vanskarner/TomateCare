package com.vanskarner.analysistracking.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import java.util.Date

@Entity(tableName = "activity_log")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "detection_inference_time") val detectionInferenceTimeMs: Long,
    @ColumnInfo(name = "classification_inference_time") val classificationInferenceTimeMs: Long,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "number_diseases_identified") val numberDiseasesIdentified: Int,
    @ColumnInfo(name = "leaf_box_coordinates") val listLeafBoxCoordinates: List<BoundingBoxData>,
    @ColumnInfo(name = "classifications") val classificationData: List<ClassificationData>,
)