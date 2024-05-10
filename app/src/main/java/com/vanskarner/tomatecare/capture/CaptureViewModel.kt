package com.vanskarner.tomatecare.capture

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CaptureViewModel : ViewModel() {

    private val _settingModel = MutableLiveData<SettingModel>()
    private val _loading = MutableLiveData<Unit>()
    private val _error = MutableLiveData<Unit>()

    val settingModel: LiveData<SettingModel> = _settingModel
    val loading: LiveData<Unit> = _loading
    val error: LiveData<Unit> = _error

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
            _loading.value = Unit
            delay(3000)
            _error.value = Unit
        }
    }

}