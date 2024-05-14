package com.vanskarner.tomatecare.performance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vanskarner.tomatecare.GenericItemModel
import com.vanskarner.tomatecare.GenericListModel

class PerformanceViewModel : ViewModel() {
    private val _threads = MutableLiveData<Int>()
    private val _processing = MutableLiveData<GenericListModel>()
    private val _models = MutableLiveData<GenericListModel>()
    private val maxThreads: Int by lazy { 5 }

    val threads: LiveData<Int> = _threads
    val processing: LiveData<GenericListModel> = _processing
    val models: LiveData<GenericListModel> = _models

    fun exampleData() {
        _threads.value = 1
        _processing.value = GenericListModel(
            listOf(
                GenericItemModel(1, "CPU"),
                GenericItemModel(2, "GPU"),
            )
        )
        _models.value = GenericListModel(
            listOf(
                GenericItemModel(1, "SqueezeNet"),
                GenericItemModel(2, "SqueezeNext"),
                GenericItemModel(3, "MobileNetV2"),
                GenericItemModel(4, "MobileNetV3Large"),
                GenericItemModel(5, "MobileNetV3Small"),
            )
        )
    }

    fun startTest(posProcessing: Int, posModels: Int) {

    }

    fun decreaseThreads() {
        _threads.value?.let {
            val newValue = it - 1
            val result = if (newValue > 0) newValue else it
            _threads.value = result
        }
    }

    fun increaseThreads() {
        _threads.value?.let {
            val newValue = it + 1
            val result = if (newValue <= maxThreads) newValue else maxThreads
            _threads.value = result
        }
    }
}