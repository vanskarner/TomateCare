package com.vanskarner.analysistracking

interface AnalysisTrackingComponent {

    suspend fun getAnalysisList(): Result<List<AnalysisData>>

}