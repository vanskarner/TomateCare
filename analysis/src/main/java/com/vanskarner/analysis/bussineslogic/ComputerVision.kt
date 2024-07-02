package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.BoundingBoxData
import com.vanskarner.analysis.ClassificationData

internal interface ComputerVision {

    suspend fun detectLeaves(imgPath: String): Result<Pair<Long, List<BoundingBoxData>>>

    suspend fun classifyLeavesWithMobileNetV3Small(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>>

    suspend fun classifyLeavesWithMobileNetV3Large(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>>

    suspend fun classifyLeavesWithMobileNetV2(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>>

    suspend fun classifyLeavesWithSqueezeNetMish(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>>

    suspend fun classifyLeavesWithNASNetMobile(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>>

}