package com.vanskarner.diseases.bussineslogic

data class DiseaseData(
    val id: Int,
    val name: String,
    val imageBase64: String,
    val symptoms: String,
)

data class DiseaseDetailData(
    val id: Int,
    val name: String,
    val imageBase64: String,
    val causalAgent: String,
    val symptoms: String,
    val developmentConditions: String,
    val control: String,
    val source: String
)
