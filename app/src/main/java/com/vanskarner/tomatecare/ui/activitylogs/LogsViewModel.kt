package com.vanskarner.tomatecare.ui.activitylogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysistracking.AnalysisTrackingComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LogsViewModel @Inject constructor(
    private val component: AnalysisTrackingComponent
) : ViewModel() {
    private val _list = MutableLiveData<List<LogModel>>()
    private val _msgDelete = MutableLiveData<Unit>()
    private val _msgNoItemSelected = MutableLiveData<Unit>()
    private val _restart = MutableLiveData<Unit>()
    private var fullList = mutableListOf<LogModel>()
    private var filterList = mutableListOf<LogModel>()

    val list: LiveData<List<LogModel>> = _list
    val msgDelete: LiveData<Unit> = _msgDelete
    val noItemSelected: LiveData<Unit> = _msgNoItemSelected
    val restart: LiveData<Unit> = _restart

    fun getData() {
        viewModelScope.launch {
            component.getAnalysisList()
                .onSuccess {
                    fullList.clear()
                    fullList.addAll(it.toListModel())
                    _list.value = fullList
                }
        }
    }

    fun checkSelections() {
        val hasSelectedItems = fullList.any { it.checked }
        if (hasSelectedItems) _msgDelete.value = Unit
        else _msgNoItemSelected.value = Unit
    }

    fun deleteSelectedItems() {
        fullList.removeIf { it.checked }
        _list.value = fullList
        _restart.value = Unit
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

}