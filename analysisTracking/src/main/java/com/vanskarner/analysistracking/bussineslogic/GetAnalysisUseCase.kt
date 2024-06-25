package com.vanskarner.analysistracking.bussineslogic

internal class GetAnalysisUseCase(
    private val repository: Repository
) {

    suspend fun execute() {
        runCatching {
            val result = repository.getAnalysisList().getOrThrow()

        }
    }

}