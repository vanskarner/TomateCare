package com.vanskarner.analysis

import com.vanskarner.analysis.bussineslogic.AnalyzePlantUseCase
import com.vanskarner.analysis.bussineslogic.DeleteAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.FindAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.GetAnalysisNoteUseCase
import com.vanskarner.analysis.bussineslogic.GetAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.GetConfigUseCase
import com.vanskarner.analysis.bussineslogic.GetTestResourcesUseCase
import com.vanskarner.analysis.bussineslogic.TestPerformanceUseCase
import com.vanskarner.analysis.bussineslogic.UpdateAnalysisNoteUseCase
import java.io.InputStream

internal class DefaultAnalysisComponent(
    private val getAnalysisUseCase: GetAnalysisUseCase,
    private val analyzePlantUseCase: AnalyzePlantUseCase,
    private val findAnalysisUseCase: FindAnalysisUseCase,
    private val getConfigUseCase: GetConfigUseCase,
    private val updateAnalysisNoteUseCase: UpdateAnalysisNoteUseCase,
    private val deleteAnalysisUseCase: DeleteAnalysisUseCase,
    private val getAnalysisNoteUseCase: GetAnalysisNoteUseCase,
    private val performanceUseCase: TestPerformanceUseCase,
    private val getTestResourcesUseCase: GetTestResourcesUseCase
) : AnalysisComponent {

    override suspend fun getAnalysis(): Result<List<AnalysisData>> {
        return getAnalysisUseCase.execute()
    }

    override suspend fun analyzePlant(
        imgPath: String,
        configData: SetConfigData
    ): Result<AnalysisDetailData> = analyzePlantUseCase.execute(imgPath, configData)

    override suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData> =
        findAnalysisUseCase.execute(id)

    override fun getConfigParams(): ConfigData = getConfigUseCase.execute()

    override suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit> =
        updateAnalysisNoteUseCase.execute(id, note)

    override suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int> =
        deleteAnalysisUseCase.execute(ids)

    override suspend fun getAnalysisNote(id: Int): Result<String> =
        getAnalysisNoteUseCase.execute(id)

    override suspend fun performanceTest(config: TestConfigData): Result<Pair<Long, Long>> =
        performanceUseCase.execute(config)

    override suspend fun getImagesUsedForTest(): Result<Pair<InputStream, InputStream>> =
        getTestResourcesUseCase.execute()

}