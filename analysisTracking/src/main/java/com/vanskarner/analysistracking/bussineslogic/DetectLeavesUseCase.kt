package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.BoundingBoxData

internal class DetectLeavesUseCase(
    private val computerVision: ComputerVision
) {

    suspend fun execute(imgPath: String): Result<Pair<Long, List<BoundingBoxData>>> {
        val detectionLeaves = computerVision.detectLeaves(imgPath)
        val noLeaves = detectionLeaves.second.isEmpty()
        if (noLeaves) return Result.failure(AnalysisError.NoLeaves)
        return Result.success(detectionLeaves)
    }

}