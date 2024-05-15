package com.vanskarner.diseases

import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData
import com.vanskarner.diseases.bussineslogic.FindDiseaseUseCase
import com.vanskarner.diseases.bussineslogic.GetDiseasesUseCase

internal class DefaultDiseasesComponent constructor(
    private val getDiseasesUseCase: GetDiseasesUseCase,
    private val findDiseaseUseCase: FindDiseaseUseCase
) : DiseasesComponent {

    override suspend fun getList(): Result<List<DiseaseData>> = getDiseasesUseCase.execute()

    override suspend fun find(diseaseId: Int): Result<DiseaseDetailData> =
        findDiseaseUseCase.execute(diseaseId)

}