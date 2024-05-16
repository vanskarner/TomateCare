package com.vanskarner.tomatecare.ui.diseases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.diseases.DiseasesComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DiseaseViewModel @Inject constructor(
    private val diseasesComponent: DiseasesComponent
) : ViewModel() {
    private val _diseases = MutableLiveData<List<DiseaseModel>>()
    private val _diseaseDetail = MutableLiveData<DiseaseDetailModel>()


    val diseases: LiveData<List<DiseaseModel>> = _diseases
    val diseaseDetail: LiveData<DiseaseDetailModel> = _diseaseDetail


    fun getDiseases() {
        viewModelScope.launch {
            diseasesComponent.getList()
                .onSuccess { _diseases.value = it.toListModel() }
        }
    }

    fun findDisease(diseaseId: Int) {
        viewModelScope.launch {
            diseasesComponent.find(diseaseId)
                .onSuccess { _diseaseDetail.value = it.toModel() }
                .onFailure {}
        }
    }

}