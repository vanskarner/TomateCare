package com.vanskarner.tomatecare.ui.diseases

import com.vanskarner.diseases.bussineslogic.DiseaseData

internal fun DiseaseData.toModel() = DiseaseModel(id, imageBase64, name, symptoms)

internal fun List<DiseaseData>.toListModel() = this.map { it.toModel() }