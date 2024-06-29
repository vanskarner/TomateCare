package com.vanskarner.diseases

import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData
import com.vanskarner.diseases.bussineslogic.FindDiseaseByKeyCodeUseCase
import com.vanskarner.diseases.bussineslogic.FindDiseaseUseCase
import com.vanskarner.diseases.bussineslogic.FindDiseasesByKeyCodesUseCase
import com.vanskarner.diseases.bussineslogic.GetDiseasesUseCase
import com.vanskarner.diseases.bussineslogic.GetNameByKeyCodeUseCase
import com.vanskarner.diseases.bussineslogic.GetNamesByKeyCodesUseCase

internal class DefaultDiseasesComponent(
    private val findDiseasesByKeyCodesUseCase: FindDiseasesByKeyCodesUseCase,
    private val getDiseasesUseCase: GetDiseasesUseCase,
    private val findDiseaseUseCase: FindDiseaseUseCase,
    private val getNameByKeyCodeUseCase: GetNameByKeyCodeUseCase,
    private val getNamesByKeyCodesUseCase: GetNamesByKeyCodesUseCase,
    private val findDiseaseByKeyCodeUseCase: FindDiseaseByKeyCodeUseCase
) : DiseasesComponent {

    override suspend fun getList(): Result<List<DiseaseData>> = getDiseasesUseCase.execute()

    override suspend fun find(diseaseId: Int): Result<DiseaseDetailData> =
        findDiseaseUseCase.execute(diseaseId)

    override suspend fun getNameByKeyCode(keyCode: String) =
        getNameByKeyCodeUseCase.execute(keyCode)

    override suspend fun getNamesByKeyCodes(keyCodes: List<String>) =
        getNamesByKeyCodesUseCase.execute(keyCodes)

    override suspend fun findByKeyCodes(keyCodes: List<String>): Result<List<DiseaseDetailData>> =
        findDiseasesByKeyCodesUseCase.execute(keyCodes)

    override suspend fun findByKeyCode(keyCode: String): Result<DiseaseDetailData> =
        findDiseaseByKeyCodeUseCase.execute(keyCode)

}