package com.vanskarner.diseases.bussineslogic

internal class GetDiseasesUseCase(private val repository: DiseasesRepository) {

    suspend fun execute() = repository.list()

}

internal class FindDiseaseUseCase(private val repository: DiseasesRepository) {

    suspend fun execute(id:Int) = repository.find(id)

}