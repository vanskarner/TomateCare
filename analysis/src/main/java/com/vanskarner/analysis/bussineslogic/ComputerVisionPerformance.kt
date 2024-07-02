package com.vanskarner.analysis.bussineslogic

import java.io.InputStream

internal interface ComputerVisionPerformance {

    suspend fun imageUsedForDetection():InputStream

    suspend fun imagesUsedForClassification():InputStream

    suspend fun testYoloV8nModel(useGPU: Boolean, threads: Int): Result<Long>

    suspend fun testMobileNetV3SmallModel(useGPU: Boolean, threads: Int): Result<Long>

    suspend fun testMobileNetV3LargeModel(useGPU: Boolean, threads: Int): Result<Long>

    suspend fun testMobileNetV2Model(useGPU: Boolean, threads: Int): Result<Long>

    suspend fun testSqueezeNetMishModel(useGPU: Boolean, threads: Int): Result<Long>

    suspend fun testNASNetMobileModel(useGPU: Boolean, threads: Int): Result<Long>

}