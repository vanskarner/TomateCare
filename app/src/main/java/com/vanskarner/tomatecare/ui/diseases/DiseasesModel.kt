package com.vanskarner.tomatecare.ui.diseases

import android.graphics.Bitmap

internal data class DiseaseModel(
    val id:Int,
    val image: Bitmap,
    val name: String,
    val symptoms: String
)

internal data class DiseaseDetailModel(
    val id: Int,
    val image: Bitmap,
    val name: String,
    val causativeAgent: String,
    val symptoms: String,
    val developmentConditions: String,
    val control: String
)