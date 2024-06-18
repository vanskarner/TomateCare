package com.vanskarner.analysistracking

data class Classification(
    val leafState: LeafState,
    val inferenceTimeMilliSeconds: Long,
    val bestPrediction: Pair<String, Float>,
    val predictions: List<Pair<String, Float>>
) {
    companion object {
        fun healthy(
            bestPrediction: Float,
            inferenceTimeMilliSeconds: Long,
            predictions: List<Pair<String, Float>>
        ) =
            Classification(
                LeafState.Healthy,
                inferenceTimeMilliSeconds,
                Pair("Healthy", bestPrediction),
                predictions
            )

        fun sick(
            bestPrediction: Pair<String, Float>,
            inferenceTimeMilliSeconds: Long,
            predictions: List<Pair<String, Float>>
        ) =
            Classification(LeafState.Sick, inferenceTimeMilliSeconds, bestPrediction, predictions)
    }
}

data class ClassificationItem(
    val leafState: LeafState,
    val bestPrediction: Pair<String, Float>,
    val predictions: List<Pair<String, Float>>
) {
    companion object {
        fun healthy(
            bestPrediction: Float,
            predictions: List<Pair<String, Float>>
        ) =
            ClassificationItem(
                LeafState.Healthy,
                Pair("Healthy", bestPrediction),
                predictions
            )

        fun sick(
            bestPrediction: Pair<String, Float>,
            predictions: List<Pair<String, Float>>
        ) =
            ClassificationItem(LeafState.Sick, bestPrediction, predictions)
    }
}

data class ClassificationByBatch(
    val inferenceTimeMilliSeconds: Long,
    val classificationList: List<ClassificationItem>
)

enum class LeafState {
    Healthy,
    Sick
}
