package com.vanskarner.tomatecare

data class GenericListModel(
    val list: List<GenericItemModel>
) {
    val descriptions: List<String>
        get() = list.map { it.description }
}

data class GenericItemModel(val id: Int, val description: String)