package com.vanskarner.diseases.main

import com.vanskarner.diseases.DefaultDiseasesComponent
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import com.vanskarner.diseases.bussineslogic.FindDiseaseByKeyCodeUseCase
import com.vanskarner.diseases.bussineslogic.FindDiseaseUseCase
import com.vanskarner.diseases.bussineslogic.FindDiseasesByKeyCodesUseCase
import com.vanskarner.diseases.bussineslogic.GetDiseasesUseCase
import com.vanskarner.diseases.bussineslogic.GetNameByKeyCodeUseCase
import com.vanskarner.diseases.bussineslogic.GetNamesByKeyCodesUseCase
import com.vanskarner.diseases.persistence.DefaultDiseasesRepository

class DiseasesComponentFactory {

    companion object {
        fun createComponent(): DiseasesComponent {
            val repository = createRepository()
            val findDiseasesByKeyCodesUseCase = FindDiseasesByKeyCodesUseCase(repository)
            val getDiseasesUseCase = GetDiseasesUseCase(repository)
            val findDiseaseUseCase = FindDiseaseUseCase(repository)
            val getNameByKeyCodeUseCase = GetNameByKeyCodeUseCase(repository)
            val getNamesByKeyCodesUseCase = GetNamesByKeyCodesUseCase(repository)
            val findDiseaseByKeyCodeUseCase = FindDiseaseByKeyCodeUseCase(repository)
            return DefaultDiseasesComponent(
                findDiseasesByKeyCodesUseCase,
                getDiseasesUseCase,
                findDiseaseUseCase,
                getNameByKeyCodeUseCase,
                getNamesByKeyCodesUseCase,
                findDiseaseByKeyCodeUseCase
            )
        }

        private fun createRepository(): DiseasesRepository = DefaultDiseasesRepository()

    }

}