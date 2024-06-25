package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisData

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