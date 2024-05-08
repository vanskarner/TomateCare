package com.vanskarner.tomatecare.activitylogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogsViewModel : ViewModel() {
    private val _list = MutableLiveData<List<LogModel>>()
    private val _item = MutableLiveData<LogModel>()

    val list: LiveData<List<LogModel>> = _list
    val item: LiveData<LogModel> = _item

    fun exampleList(){

    }

    fun exampleItem(){

    }

}