package com.vanskarner.diseases.bussineslogic

internal interface DiseasesRepository {

    suspend fun list(): Result<List<DiseaseData>>

    suspend fun find(id: Int): Result<DiseaseDetailData>

}