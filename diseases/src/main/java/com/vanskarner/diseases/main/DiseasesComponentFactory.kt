package com.vanskarner.diseases.main

import com.vanskarner.diseases.DefaultDiseasesComponent
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import com.vanskarner.diseases.bussineslogic.FindDiseaseUseCase
import com.vanskarner.diseases.bussineslogic.GetDiseasesUseCase
import com.vanskarner.diseases.bussineslogic.GetNameByKeyCodeUseCase
import com.vanskarner.diseases.bussineslogic.GetNamesByKeyCodesUseCase
import com.vanskarner.diseases.persistence.DefaultDiseasesRepository

class DiseasesComponentFactory {

    companion object {
        fun createComponent(): DiseasesComponent {
            val repository = createRepository()
            val getDiseasesUseCase = GetDiseasesUseCase(repository)
            val findDiseaseUseCase = FindDiseaseUseCase(repository)
            val getNameByKeyCodeUseCase = GetNameByKeyCodeUseCase(repository)
            val getNamesByKeyCodesUseCase = GetNamesByKeyCodesUseCase(repository)
            return DefaultDiseasesComponent(
                getDiseasesUseCase,
                findDiseaseUseCase,
                getNameByKeyCodeUseCase,
                getNamesByKeyCodesUseCase
            )
        }

        private fun createRepository(): DiseasesRepository = DefaultDiseasesRepository()

    }

}