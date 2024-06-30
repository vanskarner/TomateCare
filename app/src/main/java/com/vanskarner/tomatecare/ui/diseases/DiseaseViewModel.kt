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
    private val _moreInfo = MutableLiveData<String>()
    private val filterList = mutableListOf<DiseaseModel>()
    private val fullList = mutableListOf<DiseaseModel>()

    val diseases: LiveData<List<DiseaseModel>> = _diseases
    val diseaseDetail: LiveData<DiseaseDetailModel> = _diseaseDetail
    val moreInfo: LiveData<String> = _moreInfo

    fun startInfo(keyCode: String) {
        viewModelScope.launch {
            if (keyCode.isNotEmpty()) {
                diseasesComponent.findByKeyCode(keyCode)
                    .onSuccess { _moreInfo.value = it.name }
            }
            diseasesComponent.getList()
                .onSuccess {
                    fullList.clear()
                    fullList.addAll(it.toListModel())
                    _diseases.value = fullList
                }
        }
    }

    fun findDisease(diseaseId: Int) {
        viewModelScope.launch {
            diseasesComponent.find(diseaseId)
                .onSuccess { _diseaseDetail.value = it.toModel() }
                .onFailure {}
        }
    }

    fun filterByName(name: String) {
        viewModelScope.launch {
            filterList.clear()
            val query = name.lowercase().trim()
            for (item in fullList)
                if (item.name.lowercase().contains(query))
                    filterList.add(item)
            _diseases.value = filterList
        }
    }

}