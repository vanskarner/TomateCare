package com.vanskarner.diseases.persistence

import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData

internal fun DiseaseDTO.toData() = DiseaseData(id, name, imageBase64, symptoms)

internal fun DiseaseDTO.toDetailData() = DiseaseDetailData(
    id,
    name,
    imageBase64,
    causalAgent,
    symptoms,
    developmentConditions,
    control,
    source
)

internal fun DiseasesDTO.toListData() = diseases.map { it.toData() }