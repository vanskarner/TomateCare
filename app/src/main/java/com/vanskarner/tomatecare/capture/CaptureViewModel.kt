package com.vanskarner.tomatecare.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CaptureViewModel : ViewModel() {

    private val _settingModel = MutableLiveData<SettingModel>()

    val settingModel: LiveData<SettingModel> = _settingModel

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

}