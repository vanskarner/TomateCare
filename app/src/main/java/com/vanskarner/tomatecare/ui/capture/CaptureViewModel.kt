package com.vanskarner.tomatecare.ui.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.tomatecare.ui.common.BoundingBoxModel
import com.vanskarner.tomatecare.ui.common.toModel
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class CaptureViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {

    private val _setting = MutableLiveData<SettingModel>()
    private val _startVisibility = MutableLiveData(true)
    private val _loadingVisibility = MutableLiveData(false)
    private val _analysisFinishedVisibility = MutableLiveData(false)
    private val _defaultImage = MutableLiveData<Unit>()
    private val _error = MutableLiveData<String>()
    private val _idLog = MutableLiveData<Int>()
    private val _boundingBoxes = MutableLiveData<List<BoundingBoxModel>>()

    val setting: LiveData<SettingModel> = _setting
    val startVisibility: LiveData<Boolean> = _startVisibility
    val loadingVisibility: LiveData<Boolean> = _loadingVisibility
    val analysisFinishedVisibility: LiveData<Boolean> = _analysisFinishedVisibility
    val defaultImage: LiveData<Unit> = _defaultImage
    val error: LiveData<String> = _error
    val idLog: LiveData<Int> = _idLog
    val boundingBoxes: LiveData<List<BoundingBoxModel>> = _boundingBoxes

    private var settingModel = analysisComponent.getConfigParams().toModel()
    private var analysisId = 0

    fun getSetting() {
        _setting.value = settingModel
    }

    fun updateSetting(setting: SettingModel) {
        this.settingModel = setting
    }

    fun analyzeImage(imgPath: String) {
        showLoading()
        viewModelScope.launch {
            val analysisResult = withContext(Dispatchers.IO) {
                analysisComponent.analyzePlant(imgPath, settingModel.toData())
            }
            withContext(Dispatchers.Main) {
                analysisResult
                    .onSuccess {
                        analysisId = it.id
                        _boundingBoxes.value = it.listLeafBoxCoordinates.toModel()
                        showAnalysisFinished()
                    }
                    .onFailure { showError(errorFilter.filter(it)) }
            }
        }
    }

    fun nextScreen() {
        _idLog.value = analysisId
    }

    fun showStart() {
        _loadingVisibility.value = false
        _analysisFinishedVisibility.value = false
        _startVisibility.value = true
        _defaultImage.value = Unit
        _boundingBoxes.value = emptyList()
    }

    private fun showLoading() {
        _startVisibility.value = false
        _analysisFinishedVisibility.value = false
        _loadingVisibility.value = true
    }

    private fun showAnalysisFinished() {
        _loadingVisibility.value = false
        _startVisibility.value = false
        _analysisFinishedVisibility.value = true
    }

    private fun showError(msgError: String) {
        _analysisFinishedVisibility.value = false
        _loadingVisibility.value = false
        _startVisibility.value = true
        _defaultImage.value = Unit
        _error.value = msgError
    }

}