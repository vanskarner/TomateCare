package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData

class FakeComputerVision(
    private val detections: Pair<Long, List<BoundingBoxData>>,
    private val classification: Pair<Long, List<ClassificationData>>,
) : ComputerVision {

    override suspend fun detectLeaves(imgPath: String): Pair<Long, List<BoundingBoxData>> {
        return detections
    }

    override suspend fun classifyLeavesWithMobileNetV3Small(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        return Result.success(classification)
    }

    override suspend fun classifyLeavesWithMobileNetV3Large(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        return Result.success(classification)
    }

    override suspend fun classifyLeavesWithMobileNetV2(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        return Result.success(classification)
    }

    override suspend fun classifyLeavesWithSqueezeNetMish(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        return Result.success(classification)
    }

    override suspend fun classifyLeavesWithNASNetMobile(
        imgPath: String,
        list: List<BoundingBoxData>,
        useGPU: Boolean,
        numberThreads: Int
    ): Result<Pair<Long, List<ClassificationData>>> {
        return Result.success(classification)
    }
}