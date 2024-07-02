package com.vanskarner.tomatecare.ui.diseases

import com.vanskarner.diseases.DiseaseData
import com.vanskarner.diseases.DiseaseDetailData

internal fun DiseaseData.toModel() = DiseaseModel(id, imageBase64, name, symptoms)

internal fun List<DiseaseData>.toListModel() = this.map { it.toModel() }

internal fun DiseaseDetailData.toModel() =
    DiseaseDetailModel(id, imageBase64, name, causalAgent, symptoms, developmentConditions, control)