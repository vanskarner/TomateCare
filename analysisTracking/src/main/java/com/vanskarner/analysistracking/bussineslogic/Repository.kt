package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.AnalysisData

internal interface Repository {

    suspend fun list(): Result<List<AnalysisData>>

}