package com.vanskarner.tomatecare.activitylogs

import android.graphics.Bitmap

internal data class LogModel(
    val id: Int,
    val isHealthy: Boolean,
    val image: Bitmap,
    val state: String,
    val note: String,
    val date: String,
    var checked: Boolean
)