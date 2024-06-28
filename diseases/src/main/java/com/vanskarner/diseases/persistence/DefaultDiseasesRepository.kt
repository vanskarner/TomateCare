package com.vanskarner.diseases.persistence

import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData
import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class DefaultDiseasesRepository : DiseasesRepository {

    override suspend fun list(): Result<List<DiseaseData>> {
        val listDTO = loadDiseasesFromJsonFile()
        return Result.success(listDTO.toListData())
    }

    override suspend fun find(id: Int): Result<DiseaseDetailData> {
        val listDTO = loadDiseasesFromJsonFile()
        val itemDTO = listDTO.diseases.find { it.id == id }
        return if (itemDTO != null) Result.success(itemDTO.toDetailData())
        else Result.failure(DiseasesPersistenceError.NotFound)
    }

    override suspend fun getNameByKeyCode(keyCode: String): Result<String> {
        val listDTO = loadDiseasesFromJsonFile()
        val itemDTO = listDTO.diseases.find { it.keyCode == keyCode }
        return Result.success(itemDTO?.name ?: "")
    }

    override suspend fun getNamesByKeyCodes(keyCodes: List<String>): Result<List<String>> {
        val listDTO = loadDiseasesFromJsonFile()
        val names = keyCodes.map { keyCode ->
            listDTO.diseases.find { it.keyCode == keyCode }?.name ?: ""
        }
        return Result.success(names)
    }

    override suspend fun findByKeyCodes(keyCodes: List<String>): Result<List<DiseaseDetailData>> {
        val listDTO = loadDiseasesFromJsonFile()
        val diseases = keyCodes.map { keyCode ->
            listDTO.diseases.find { it.keyCode == keyCode }?.toDetailData()
                ?: DiseaseDetailData.empty()
        }
        return Result.success(diseases)
    }

    private fun loadDiseasesFromJsonFile(): DiseasesDTO {
        return try {
            val jsonFilePath = "/diseases.json"
            val fileUrl = this::class.java.getResource(jsonFilePath)
            val jsonString = fileUrl?.readText() ?: ""
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            DiseasesDTO(emptyList())
        }
    }

}