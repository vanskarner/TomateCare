package com.vanskarner.tomatecare.identification

import android.graphics.Bitmap

data class IdentificationDetailModel(
    val id: Int,
    val creationDate: String,
    val plantImage: Bitmap,
    val leavesImage: List<LeafModel>,
    val description: String
)

data class LeafModel(
    val image: Bitmap,
    val isHealthy: Boolean,
    val diseases: String,
    val probability: Float
)