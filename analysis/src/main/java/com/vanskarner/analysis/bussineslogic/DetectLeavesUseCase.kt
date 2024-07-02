package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.BoundingBoxData

internal class DetectLeavesUseCase(
    private val computerVision: ComputerVision
) {

    suspend fun execute(imgPath: String): Result<Pair<Long, List<BoundingBoxData>>> {
        return computerVision.detectLeaves(imgPath)
            .mapCatching { detectionLeaves ->
                val noLeaves = detectionLeaves.second.isEmpty()
                if (noLeaves) throw AnalysisError.NoLeaves
                detectionLeaves
            }
    }

}