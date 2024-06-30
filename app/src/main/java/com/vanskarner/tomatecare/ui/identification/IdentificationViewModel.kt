package com.vanskarner.tomatecare.ui.identification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.tomatecare.ui.common.toModel
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class IdentificationViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val diseasesComponent: DiseasesComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _identification = MutableLiveData<IdentificationDetailModel>()
    private val _note = MutableLiveData<String>()
    private val _summary = MutableLiveData<SummaryModel>()
    private val _leafInfo = MutableLiveData<LeafInfoModel>()
    private val _error = MutableLiveData<String>()
    private val _updatedNote = MutableLiveData<Unit>()

    val identification: LiveData<IdentificationDetailModel> = _identification
    val note: LiveData<String> = _note
    val summary: LiveData<SummaryModel> = _summary
    val leafInfo: LiveData<LeafInfoModel> = _leafInfo
    val error: LiveData<String> = _error
    val updatedNote: LiveData<Unit> = _updatedNote

    private var currentId = -1
    private var currentSummary = SummaryModel.empty()

    fun showAnalysis(analysisId: Int) {
        viewModelScope.launch {
            analysisComponent.findAnalysisDetail(analysisId)
                .onSuccess {
                    val leafModelList = it.listLeafBoxCoordinates.toModel()
                        .zip(it.classificationData)
                        .map { boxLeaf -> boxLeaf.second.toLeafModel(it.imagePath,boxLeaf.first) }
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val creationDate = formatter.format(it.date)
                    val identificationDetailModel =
                        it.toIdentificationDetailModel(creationDate, leafModelList)
                    val recommendations = diseasesComponent.findByKeyCodes(it.diseaseKeyCodes)
                        .getOrDefault(emptyList())
                        .map { disease -> RecommendationModel(disease.name, disease.control) }
                    currentSummary = it.toSummaryModel(recommendations)
                    currentId = identificationDetailModel.id
                    _identification.value = identificationDetailModel
                }
                .onFailure { showError(it) }
        }
    }

    fun saveNote(text: String) {
        viewModelScope.launch {
            analysisComponent.updateAnalysisNote(currentId, text)
                .onSuccess { _updatedNote.value = Unit }
                .onFailure { showError(it) }
        }
    }

    fun getNote() {
        viewModelScope.launch {
            analysisComponent.getAnalysisNote(currentId)
                .onSuccess { _note.value = it }
                .onFailure { showError(it) }
        }
    }

    fun getSummary() {
        _summary.value = currentSummary
    }

    fun getLeafInfo(leafModel: LeafModel) {
        viewModelScope.launch {
            val symptoms = diseasesComponent.findByKeyCode(leafModel.keyCode)
                .getOrNull()?.symptoms ?: ""
            _leafInfo.value = leafModel.toLeafInfoModel(symptoms)
        }
    }

    private fun showError(error: Throwable) {
        _error.value = errorFilter.filter(error)
    }

}