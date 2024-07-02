package com.vanskarner.tomatecare.ui.errors

interface ErrorFilter {

    fun filter(throwable: Throwable):String

}