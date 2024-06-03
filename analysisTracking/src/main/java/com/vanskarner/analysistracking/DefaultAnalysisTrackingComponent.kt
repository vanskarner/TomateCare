package com.vanskarner.analysistracking

import com.vanskarner.analysistracking.bussineslogic.GetAnalysisTracking

internal class DefaultAnalysisTrackingComponent(
    private val getAnalysisTracking: GetAnalysisTracking
) : AnalysisTrackingComponent {

    override suspend fun getAnalysisList(): Result<List<AnalysisData>> {
        return getAnalysisTracking.execute()
    }

}