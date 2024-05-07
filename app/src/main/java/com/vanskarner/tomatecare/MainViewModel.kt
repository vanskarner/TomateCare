package com.vanskarner.tomatecare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _onBack = MutableLiveData<Unit>()

    val onBack: LiveData<Unit> = _onBack

    fun onBackPressed() {
        _onBack.value = Unit
    }

}