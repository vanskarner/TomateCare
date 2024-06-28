package com.vanskarner.tomatecare.ui.common

data class GenericListModel(
    val list: List<GenericItemModel>
) {
    val descriptions: List<String>
        get() = list.map { it.description }
}

data class GenericItemModel(val id: Int, val description: String)

data class BoundingBoxModel(
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
