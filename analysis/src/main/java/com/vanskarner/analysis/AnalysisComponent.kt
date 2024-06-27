package com.vanskarner.analysis

interface AnalysisComponent {

    suspend fun getAnalysis(): Result<List<AnalysisData>>

    suspend fun analyzePlant(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData>

    suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData>

    fun getConfigParams(): ConfigData

    suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit>

    suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int>

}