package com.vanskarner.analysistracking

import com.vanskarner.analysistracking.bussineslogic.GetAnalysisTrackingUseCases

internal class DefaultAnalysisTrackingComponent(
    private val getAnalysisTrackingUseCases: GetAnalysisTrackingUseCases
) : AnalysisTrackingComponent {

    override suspend fun getAnalysisList(): Result<List<AnalysisData>> {
        return getAnalysisTrackingUseCases.execute()
    }

}