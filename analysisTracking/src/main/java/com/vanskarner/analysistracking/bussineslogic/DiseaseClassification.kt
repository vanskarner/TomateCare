package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import java.nio.ByteBuffer

interface DiseaseClassification {

    suspend fun predict(byteBuffer: ByteBuffer): Result<ClassificationData>

    suspend fun detectLeaves(imgPath: String): Pair<Long, List<BoundingBoxData>>

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