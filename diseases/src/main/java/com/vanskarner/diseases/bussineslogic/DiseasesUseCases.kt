package com.vanskarner.diseases.bussineslogic

internal class GetDiseasesUseCase(private val repository: DiseasesRepository) {

    suspend fun execute() = repository.list()

}

internal class FindDiseaseUseCase(private val repository: DiseasesRepository) {

    suspend fun execute(id: Int) = repository.find(id)

}

internal class GetNameByKeyCodeUseCase(private val repository: DiseasesRepository) {

    suspend fun execute(keyCode: String) = repository.getNameByKeyCode(keyCode)

}

internal class GetNamesByKeyCodesUseCase(private val repository: DiseasesRepository) {

    suspend fun execute(keyCodes: List<String>) = repository.getNamesByKeyCodes(keyCodes)

}