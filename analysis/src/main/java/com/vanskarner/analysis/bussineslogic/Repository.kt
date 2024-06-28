package com.vanskarner.analysis.bussineslogic

import com.vanskarner.analysis.AnalysisData
import com.vanskarner.analysis.AnalysisDetailData

internal interface Repository {

    suspend fun list(): Result<List<AnalysisData>>

    suspend fun saveAnalysis(analysisDetailData: AnalysisDetailData): Result<Int>

    suspend fun findAnalysis(id:Int): Result<AnalysisDetailData>

    suspend fun getAnalysisList(): Result<List<AnalysisDetailData>>

    suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit>

    suspend fun deleteAnalysis(ids: List<Int>): Result<Int>

    suspend fun findAnalysisNote(id:Int):Result<String>

}