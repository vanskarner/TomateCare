package com.vanskarner.diseases.bussineslogic

class FakeDiseasesRepository(
    private val list: List<DiseaseData>,
    private val item: DiseaseDetailData,
    private val nameDisease: String,
    private val nameDiseases: List<String>,
    private val listByKeCodes: List<DiseaseDetailData>
) : DiseasesRepository {

    override suspend fun list(): Result<List<DiseaseData>> {
        return Result.success(list)
    }

    override suspend fun find(id: Int): Result<DiseaseDetailData> {
        return Result.success(item)
    }

    override suspend fun getNameByKeyCode(keyCode: String): Result<String> {
        return Result.success(nameDisease)
    }

    override suspend fun getNamesByKeyCodes(keyCodes: List<String>): Result<List<String>> {
        return Result.success(nameDiseases)
    }

    override suspend fun findByKeyCodes(keyCodes: List<String>): Result<List<DiseaseDetailData>> {
        return Result.success(listByKeCodes)
    }

}