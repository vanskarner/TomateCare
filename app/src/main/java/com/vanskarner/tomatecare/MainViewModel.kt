package com.vanskarner.tomatecare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _hideBottomNav = MutableLiveData<Unit>()
    private val _showBottomNav = MutableLiveData<Selection>()

    val hideBottomNav: LiveData<Unit> = _hideBottomNav
    val showBottomNav: LiveData<Selection> = _showBottomNav

    fun hideBottomNavigation() {
        _hideBottomNav.value = Unit
    }

    fun showBottomNavigation(selection: Selection) {
        _showBottomNav.value = selection
    }

}