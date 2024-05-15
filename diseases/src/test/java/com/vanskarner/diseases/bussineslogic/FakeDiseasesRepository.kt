package com.vanskarner.diseases.bussineslogic

class FakeDiseasesRepository(
    private val list: List<DiseaseData>,
    private val item: DiseaseDetailData
) : DiseasesRepository {

    override suspend fun list(): Result<List<DiseaseData>> {
        return Result.success(list)
    }

    override suspend fun find(id: Int): Result<DiseaseDetailData> {
        return Result.success(item)
    }

}