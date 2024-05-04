package com.vanskarner.tomatecare.diseases

import com.vanskarner.singleadapter.BindItem

data class DiseaseModel(
    val id:Int,
    val image: String,
    val name: String,
    val symptoms: String
):BindItem