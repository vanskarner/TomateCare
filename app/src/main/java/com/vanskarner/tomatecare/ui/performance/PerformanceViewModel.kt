package com.vanskarner.tomatecare.ui.performance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.analysis.TestConfigData
import com.vanskarner.tomatecare.ui.common.GenericItemModel
import com.vanskarner.tomatecare.ui.common.GenericListModel
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class PerformanceViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _threads = MutableLiveData<Int>()
    private val _processing = MutableLiveData<GenericListModel>()
    private val _models = MutableLiveData<GenericListModel>()
    private val _error = MutableLiveData<String>()
    private val _detectionImage = MutableLiveData<InputStream>()
    private val _classificationImage = MutableLiveData<InputStream>()
    private val _loading = MutableLiveData<Boolean>()
    private val _inferenceResults = MutableLiveData<Pair<String, String>>()

    val threads: LiveData<Int> = _threads
    val processing: LiveData<GenericListModel> = _processing
    val models: LiveData<GenericListModel> = _models
    val error: LiveData<String> = _error
    val detectionImage: LiveData<InputStream> = _detectionImage
    val classificationImage: LiveData<InputStream> = _classificationImage
    val loading: LiveData<Boolean> = _loading
    val inferenceResults: LiveData<Pair<String, String>> = _inferenceResults

    private var maxThreads: Int = 0

    fun showConfigForTest() {
        val config = analysisComponent.getConfigParams()
        maxThreads = config.maxThreads
        _threads.value = maxThreads
        _processing.value = GenericListModel(config.processingList
            .mapIndexed { index, value -> GenericItemModel(index, value) })
        _models.value = GenericListModel(config.modelList
            .mapIndexed { index, value -> GenericItemModel(index, value) })
    }

    fun showTestImageForDetection() {
        viewModelScope.launch {
            analysisComponent.getImagesUsedForTest()
                .onSuccess {
                    it.second.close()
                    _detectionImage.value = it.first
                }
                .onFailure { showError(it) }
        }
    }

    fun showTestImageForClassification() {
        viewModelScope.launch {
            analysisComponent.getImagesUsedForTest()
                .onSuccess {
                    it.first.close()
                    _classificationImage.value = it.second
                }
                .onFailure { showError(it) }
        }
    }

    fun startTest(posProcessing: Int, posModels: Int) {
        _loading.value = true
        val config = TestConfigData(
            numberThreads = _threads.value ?: 0,
            processing = _processing.value?.list?.get(posProcessing)?.description ?: "",
            model = _models.value?.list?.get(posModels)?.description ?: ""
        )
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { analysisComponent.performanceTest(config) }
            withContext(Dispatchers.Main) {
                result
                    .onSuccess {
                        _loading.value = false
                        val detectionInference = it.first
                        val averageClassificationInference = it.second / 10
                        _inferenceResults.value =
                            Pair("$detectionInference ms", "$averageClassificationInference ms")
                    }
                    .onFailure {
                        _loading.value = false
                        showError(it)
                    }
            }
        }
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

    private fun showError(error: Throwable) {
        _error.value = errorFilter.filter(error)
    }

}