package com.vanskarner.analysis.computervision

import org.tensorflow.lite.DataType

internal const val CLASSIFICATION_IMG_SIZE = 224
internal const val CLASSIFICATION_CHANNELS = 3
internal const val CLASSIFICATION_NUM_CLASSES = 10
internal val CLASSIFICATION_INPUT_IMAGE_TYPE = DataType.FLOAT32
internal val CLASSIFICATION_OUTPUT_IMAGE_TYPE = DataType.FLOAT32
internal val CLASSIFICATION_CLASSES = listOf(
    "bacterial_spot",
    "early_blight",
    "healthy",
    "late_blight",
    "leaf_mold",
    "mosaic_virus",
    "septoria_leaf_spot",
    "target_spot",
    "twospotted_spider_mite",
    "yellow_leaf_curl_virus"
)