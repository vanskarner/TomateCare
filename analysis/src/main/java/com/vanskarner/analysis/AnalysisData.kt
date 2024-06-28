package com.vanskarner.analysis

import java.util.Date

data class ClassificationData(
    val leafState: LeafState,
    val bestPrediction: Pair<String, Float>,
    val predictions: List<Pair<String, Float>>
) {
    companion object {
        fun healthy(
            bestPrediction: Pair<String, Float>,
            predictions: List<Pair<String, Float>>
        ) =
            ClassificationData(
                LeafState.Healthy,
                bestPrediction,
                predictions
            )

        fun sick(
            bestPrediction: Pair<String, Float>,
            predictions: List<Pair<String, Float>>
        ) =
            ClassificationData(
                LeafState.Sick,
                bestPrediction,
                predictions
            )
    }
}

data class BoundingBoxData(
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    val cx: Float,
    val cy: Float,
    val w: Float,
    val h: Float,
    val cnf: Float,
    val cls: Int,
    val clsName: String
)

data class AnalysisData(
    val id: Int,
    val isHealthy: Boolean,
    val imagePath: String,
    val numberDiseases: Int,
    val note: String,
    val date: Date,
)

data class AnalysisDetailData(
    val id: Int = 0,
    val imagePath: String,
    val date: Date,
    val detectionInferenceTimeMs: Long,
    val classificationInferenceTimeMs: Long,
    val note: String = "",
    val numberDiseasesIdentified: Int,
    val diseaseKeyCodes:List<String> = emptyList(),
    val listLeafBoxCoordinates: List<BoundingBoxData>,
    val classificationData: List<ClassificationData>,
    val leafDetectionModel:String,
    val leafClassificationModel:String,
    val threadsUsed:String,
    val processing:String,
)

data class SetConfigData(
    val numberResults: Int,
    val numberThreads: Int,
    val processing: String,
    val model: String
)

data class ConfigData(
    val maxResults: Int,
    val maxThreads: Int,
    val processingList: List<String>,
    val modelList: List<String>
)

enum class LeafState {
    Healthy,
    Sick
}