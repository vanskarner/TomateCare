package com.vanskarner.analysistracking.bussineslogic

internal class GetAnalysisTrackingUseCases(private val repository: Repository) {
    suspend fun execute() = repository.list()

}