package com.vanskarner.tomatecare.ui.capture

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CaptureViewModel @Inject constructor(): ViewModel() {

    private val _settingModel = MutableLiveData<SettingModel>()
    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Unit>()
    private val _idLog = MutableLiveData<Int>()
    private val _imageToAnalyze = MutableLiveData<Bitmap>()

    val settingModel: LiveData<SettingModel> = _settingModel
    val loading: LiveData<Boolean> = _loading
    val error: LiveData<Unit> = _error
    val idLog: LiveData<Int> = _idLog
    val imageToAnalyze: LiveData<Bitmap> = _imageToAnalyze

    private var myModel = SettingModel(
        0.3f,
        3,
        3,
        listOf("CPU", "GPU"),
        listOf(
            "SqueezeNet",
            "SqueezeNext",
            "MobileNetV2",
            "MobileNetV3Large",
            "MobileNetV3Small"
        ),
        0.8f,
        5,
        5,
        0,
        1
    )

    fun getSetting() {
        _settingModel.value = myModel
    }

    fun updateSetting(setting: SettingModel) {
        this.myModel = setting
    }

    fun analyzeImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _loading.value = true
            _imageToAnalyze.value = bitmap
            delay(3000)
//            _error.value = Unit
            _idLog.value = 12
        }
    }

    fun analyzeImage2(imgPath: String) {
        viewModelScope.launch {
            _loading.value = true
            _imageToAnalyze.value = BitmapFactory.decodeFile(imgPath)
            delay(3000)
//            _error.value = Unit
            _idLog.value = 12
        }
    }

}