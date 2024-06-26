package com.vanskarner.analysistracking

import com.vanskarner.analysistracking.bussineslogic.AnalyzePlantUseCase
import com.vanskarner.analysistracking.bussineslogic.DeleteAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.FindAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.GetAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.GetConfigUseCase
import com.vanskarner.analysistracking.bussineslogic.UpdateAnalysisNoteUseCase

internal class DefaultAnalysisTrackingComponent(
    private val getAnalysisUseCase: GetAnalysisUseCase,
    private val analyzePlantUseCase: AnalyzePlantUseCase,
    private val findAnalysisUseCase: FindAnalysisUseCase,
    private val getConfigUseCase: GetConfigUseCase,
    private val updateAnalysisNoteUseCase: UpdateAnalysisNoteUseCase,
    private val deleteAnalysisUseCase: DeleteAnalysisUseCase
) : AnalysisTrackingComponent {

    override suspend fun getAnalysis(): Result<List<AnalysisData>> {
        return getAnalysisUseCase.execute()
    }

    override suspend fun analyzePlant(
        imgPath: String,
        configData: SetConfigData
    ): Result<AnalysisDetailData> = analyzePlantUseCase.execute(imgPath, configData)

    override suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData> =
        findAnalysisUseCase.execute(id)

    override suspend fun getConfigParams(): ConfigData = getConfigUseCase.execute()

    override suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit> =
        updateAnalysisNoteUseCase.execute(id, note)

    override suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int> =
        deleteAnalysisUseCase.execute(ids)

}