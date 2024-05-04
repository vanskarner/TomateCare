package com.vanskarner.tomatecare.diseases

import com.vanskarner.singleadapter.BindItem

data class DiseaseDetailModel(
    val id: Int,
    val images: List<String>,
    val name: String,
    val causativeAgent: String,
    val symptoms: String,
    val developmentConditions: String,
    val control: String
) : BindItem