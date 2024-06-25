package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData

class FakeDiseaseComponent(
    private val diseaseName: String,
    private val diseaseNames: List<String>,
): DiseasesComponent {
    override suspend fun getList(): Result<List<DiseaseData>> {
        TODO("Not yet implemented")
    }

    override suspend fun find(diseaseId: Int): Result<DiseaseDetailData> {
        TODO("Not yet implemented")
    }

    override suspend fun getNameByKeyCode(keyCode: String): Result<String> {
        return Result.success(diseaseName)
    }

    override suspend fun getNamesByKeyCodes(keyCodes: List<String>): Result<List<String>> {
        return Result.success(diseaseNames)
    }
}