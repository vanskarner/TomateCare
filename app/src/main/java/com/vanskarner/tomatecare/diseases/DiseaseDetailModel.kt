package com.vanskarner.tomatecare.diseases

import android.graphics.Bitmap

data class DiseaseDetailModel(
    val id: Int,
    val image: Bitmap,
    val name: String,
    val causativeAgent: String,
    val symptoms: String,
    val developmentConditions: String,
    val control: String
)