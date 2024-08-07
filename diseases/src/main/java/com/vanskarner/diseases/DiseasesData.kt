package com.vanskarner.diseases

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
) {
    companion object {
        fun empty() = DiseaseDetailData(
            id = 0,
            name = "",
            imageBase64 = "",
            causalAgent = "",
            symptoms = "",
            developmentConditions = "",
            control = "",
            source = ""
        )
    }
}
