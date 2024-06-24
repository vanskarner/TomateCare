package com.vanskarner.analysistracking

sealed class AnalysisError : Exception() {

    object NotFound : AnalysisError() {
        private fun readResolve(): Any = NotFound
    }

}