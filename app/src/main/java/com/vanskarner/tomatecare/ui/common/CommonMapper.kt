package com.vanskarner.tomatecare.ui.common

import com.vanskarner.analysis.BoundingBoxData

fun List<BoundingBoxData>.toModel() = this.map { it.toModel() }

internal fun BoundingBoxData.toModel() =
    BoundingBoxModel(x1, y1, x2, y2, cx, cy, w, h, cnf, cls, clsName)