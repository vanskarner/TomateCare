package com.vanskarner.analysistracking.bussineslogic

internal class GetAnalysisTracking(private val repository: Repository) {
    suspend fun execute() = repository.list()

}