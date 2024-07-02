package com.vanskarner.diseases.bussineslogic

import com.vanskarner.diseases.DiseaseData
import com.vanskarner.diseases.DiseaseDetailData

internal interface DiseasesRepository {

    suspend fun list(): Result<List<DiseaseData>>

    suspend fun find(id: Int): Result<DiseaseDetailData>

    suspend fun getNameByKeyCode(keyCode: String): Result<String>

    suspend fun getNamesByKeyCodes(keyCodes: List<String>): Result<List<String>>

    suspend fun findByKeyCodes(keyCodes: List<String>): Result<List<DiseaseDetailData>>

    suspend fun findByKeyCode(keyCode: String): Result<DiseaseDetailData>

}