package com.vanskarner.tomatecare.ui.capture

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CaptureViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent
) : ViewModel() {

    private val _settingModel = MutableLiveData<SettingModel>()
    private val _start = MutableLiveData(true)
    private val _loading = MutableLiveData(false)
    private val _analysisFinished = MutableLiveData(false)
    private val _defaultImage = MutableLiveData<Unit>()
    private val _error = MutableLiveData<Throwable>()
    private val _idLog = MutableLiveData<Int>()
    private val _imageToAnalyze = MutableLiveData<Bitmap>()

    val settingModel: LiveData<SettingModel> = _settingModel
    val start: LiveData<Boolean> = _start
    val loading: LiveData<Boolean> = _loading
    val analysisFinished: LiveData<Boolean> = _analysisFinished
    val defaultImage: LiveData<Unit> = _defaultImage
    val error: LiveData<Throwable> = _error
    val idLog: LiveData<Int> = _idLog
    val imageToAnalyze: LiveData<Bitmap> = _imageToAnalyze

    private var myModel = analysisComponent.getConfigParams().toModel()

    fun getSetting() {
        _settingModel.value = myModel
    }

    fun updateSetting(setting: SettingModel) {
        this.myModel = setting
    }

    fun nextScreen() {
        _idLog.value = 12
    }

    fun otherImage() {
        _analysisFinished.value = false
        _start.value = true
        _defaultImage.value = Unit
    }

    private fun showError() {
        _loading.value = false
        _start.value = true
        _defaultImage.value = Unit
        _error.value = Exception("Something went wrong")
    }

    fun analyzeImage(imgPath: String) {
        viewModelScope.launch {
            _start.value = false
            _loading.value = true
            _imageToAnalyze.value = BitmapFactory.decodeFile(imgPath)
            delay(3000)

            _loading.value = false
            _analysisFinished.value = true
            /*showError()*/
        }
    }

}