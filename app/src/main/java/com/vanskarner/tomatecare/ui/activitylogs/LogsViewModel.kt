package com.vanskarner.tomatecare.ui.activitylogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class LogsViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _list = MutableLiveData<List<LogModel>>()
    private val _msgDelete = MutableLiveData<Unit>()
    private val _msgNoItemSelected = MutableLiveData<Unit>()
    private val _restart = MutableLiveData<Unit>()
    private var _error = MutableLiveData<String>()
    private var fullList = mutableListOf<LogModel>()
    private var filterList = mutableListOf<LogModel>()

    val list: LiveData<List<LogModel>> = _list
    val msgDelete: LiveData<Unit> = _msgDelete
    val noItemSelected: LiveData<Unit> = _msgNoItemSelected
    val restart: LiveData<Unit> = _restart
    val error: LiveData<String> = _error

    fun getData() {
        viewModelScope.launch {
            analysisComponent.getAnalysis()
                .onSuccess {
                    fullList.clear()
                    val logModelList = it.map { item ->
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val formattedDate = formatter.format(item.date)
                        item.toModel(formattedDate)
                    }
                    fullList.addAll(logModelList)
                    _list.value = fullList
                }.onFailure { showError(it) }
        }
    }

    fun checkSelections() {
        val hasSelectedItems = fullList.any { it.checked }
        if (hasSelectedItems) _msgDelete.value = Unit
        else _msgNoItemSelected.value = Unit
    }

    fun deleteSelectedItems() {
        viewModelScope.launch {
            val idsToDelete = fullList.filter { it.checked }.map { it.id }
            if (idsToDelete.isNotEmpty()) {
                println("DEBUG-MD")
                analysisComponent.deleteAnalysisByIds(idsToDelete)
                    .onSuccess {
                        getData()
                        _restart.value = Unit
                    }
                    .onFailure { showError(it) }
            }
        }
    }

    fun filterByNote(name: String) {
        viewModelScope.launch {
            filterList.clear()
            val query = name.lowercase().trim()
            for (item in fullList)
                if (item.note.lowercase().contains(query))
                    filterList.add(item)
            _list.value = filterList
        }
    }

    private fun showError(error: Throwable) {
        _error.value = errorFilter.filter(error)
    }

}