package com.vanskarner.analysis.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData
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
    @ColumnInfo(name = "detection_model") val leafDetectionModel:String,
    @ColumnInfo(name = "classification_model") val leafClassificationModel:String,
    @ColumnInfo(name = "threads_used") val threadsUsed:String,
    @ColumnInfo(name = "processing") val processing:String,
)