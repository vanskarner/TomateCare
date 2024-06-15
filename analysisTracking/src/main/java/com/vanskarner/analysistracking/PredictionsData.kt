package com.vanskarner.analysistracking

data class Predictions(
    val leafState: LeafState,
    val name: String,
    val probability: Float,
    val allProbabilities: List<Pair<String, Float>>
)

enum class LeafState {
    Healthy,
    Sick
}