package com.vanskarner.diseases.main

import com.vanskarner.diseases.DefaultDiseasesComponent
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import com.vanskarner.diseases.bussineslogic.FindDiseaseUseCase
import com.vanskarner.diseases.bussineslogic.GetDiseasesUseCase
import com.vanskarner.diseases.persistence.DefaultDiseasesRepository

class DiseasesComponentFactory {

    companion object{
        fun createComponent(): DiseasesComponent {
            val repository = createRepository()
            val getDiseasesUseCase = GetDiseasesUseCase(repository)
            val findDiseaseUseCase = FindDiseaseUseCase(repository)
            return DefaultDiseasesComponent(getDiseasesUseCase, findDiseaseUseCase)
        }

        private fun createRepository(): DiseasesRepository = DefaultDiseasesRepository()

    }

}