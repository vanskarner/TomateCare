package com.vanskarner.analysistracking

data class Predictions(
    val leafState: LeafState,
    val bestPrediction: Pair<String, Float>,
    val predictions: List<Pair<String, Float>>
) {
    companion object {
        fun healthy(bestPrediction: Float, predictions: List<Pair<String, Float>>) =
            Predictions(LeafState.Healthy, Pair("Healthy", bestPrediction), predictions)

        fun sick(bestPrediction: Pair<String, Float>, predictions: List<Pair<String, Float>>) =
            Predictions(LeafState.Sick, bestPrediction, predictions)
    }
}

enum class LeafState {
    Healthy,
    Sick
}
