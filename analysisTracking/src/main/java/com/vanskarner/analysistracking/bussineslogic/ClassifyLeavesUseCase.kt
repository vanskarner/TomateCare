package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import com.vanskarner.analysistracking.SetConfigData

internal class ClassifyLeavesUseCase(
    private val validateConfigUseCase: ValidateConfigUseCase,
    private val computerVision: ComputerVision,
) {

    suspend fun execute(
        imgPath: String,
        boundingBoxes: List<BoundingBoxData>,
        setConfigData: SetConfigData
    ): Result<Pair<Long, List<ClassificationData>>> = runCatching {
        if (boundingBoxes.isEmpty()) return Result.failure(AnalysisError.NoLeaves)
        val validConfig = validateConfigUseCase.execute(setConfigData).getOrThrow()
        val useGPU = validConfig.processing == "GPU"
        return when (validConfig.model) {
            "MobileNetV3Small" -> computerVision.classifyLeavesWithMobileNetV3Small(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )

            "MobileNetV3Large" -> computerVision.classifyLeavesWithMobileNetV3Large(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )

            "MobileNetV2" -> computerVision.classifyLeavesWithMobileNetV2(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )

            "SqueezeNetMish" -> computerVision.classifyLeavesWithSqueezeNetMish(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )

            "NASNetMobile" -> computerVision.classifyLeavesWithNASNetMobile(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )

            else -> computerVision.classifyLeavesWithMobileNetV3Small(
                imgPath,
                boundingBoxes,
                useGPU,
                validConfig.numberThreads
            )
        }
    }

}