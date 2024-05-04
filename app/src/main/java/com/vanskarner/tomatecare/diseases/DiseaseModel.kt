package com.vanskarner.tomatecare.diseases

import android.graphics.Bitmap

data class DiseaseModel(
    val id:Int,
    val image: Bitmap,
    val name: String,
    val symptoms: String
)