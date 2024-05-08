package com.vanskarner.tomatecare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _bottomNavVisibility = MutableLiveData<Boolean>()
    private val _markerInDiseases = MutableLiveData<Unit>()

    val bottomNavVisibility: LiveData<Boolean> = _bottomNavVisibility
    val markerInDiseases: LiveData<Unit> = _markerInDiseases

    fun setVisibilityBottomNav(isVisible: Boolean) {
        _bottomNavVisibility.value = isVisible
    }

    fun showMarkerInDiseases() {
        _markerInDiseases.value = Unit
    }

}