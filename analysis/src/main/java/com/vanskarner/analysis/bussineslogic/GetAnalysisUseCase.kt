package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisData

internal class GetAnalysisUseCase(
    private val repository: Repository
) {

    suspend fun execute(): Result<List<AnalysisData>> = runCatching {
        val detailList = repository.getAnalysisList().getOrThrow()
        val list = detailList.map {
            AnalysisData(
                it.id,
                it.numberDiseasesIdentified == 0,
                it.imagePath,
                it.numberDiseasesIdentified,
                it.note,
                it.date
            )
        }
        return Result.success(list)
    }

}