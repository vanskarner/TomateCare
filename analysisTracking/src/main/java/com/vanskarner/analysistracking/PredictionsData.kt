package com.vanskarner.analysistracking

data class Classification(
    val leafState: LeafState,
    val bestPrediction: Pair<String, Float>,
    val predictions: List<Pair<String, Float>>
) {
    companion object {
        fun healthy(
            bestPrediction: Pair<String, Float>,
            predictions: List<Pair<String, Float>>
        ) =
            Classification(
                LeafState.Healthy,
                bestPrediction,
                predictions
            )

        fun sick(
            bestPrediction: Pair<String, Float>,
            predictions: List<Pair<String, Float>>
        ) =
            Classification(
                LeafState.Sick,
                bestPrediction,
                predictions)
    }
}

enum class LeafState {
    Healthy,
    Sick
}

data class BoundingBox (
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
