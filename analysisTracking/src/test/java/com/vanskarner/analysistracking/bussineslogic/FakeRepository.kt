package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisData
import com.vanskarner.analysistracking.AnalysisDetailData

class FakeRepository(
    private val analysisDetailData: AnalysisDetailData,
    private val analysisList: List<AnalysisDetailData>
) : Repository {

    override suspend fun list(): Result<List<AnalysisData>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAnalysis(analysisDetailData: AnalysisDetailData): Result<Int> {
        return Result.success(1)
    }

    override suspend fun findAnalysis(id: Int): Result<AnalysisDetailData> {
        return Result.success(analysisDetailData)
    }

    override suspend fun getAnalysisList(): Result<List<AnalysisDetailData>> {
        return Result.success(analysisList)
    }

    override suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteAnalysis(ids: List<Int>): Result<Int> {
        return Result.success(1)
    }

}