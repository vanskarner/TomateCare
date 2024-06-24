package com.vanskarner.analysistracking

sealed class AnalysisError : Exception() {

    object NotFound : AnalysisError() {
        private fun readResolve(): Any = NotFound
    }

    object InvalidConfig : AnalysisError(){
        private fun readResolve(): Any = InvalidConfig
    }

}