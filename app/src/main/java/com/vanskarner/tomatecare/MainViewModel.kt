package com.vanskarner.tomatecare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _bottomNavVisibility = MutableLiveData<Boolean>()

    val bottomNavVisibility: LiveData<Boolean> = _bottomNavVisibility

    fun setVisibilityBottomNav(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }

}