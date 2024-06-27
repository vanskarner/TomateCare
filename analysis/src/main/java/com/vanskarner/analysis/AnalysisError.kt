package com.vanskarner.analysis

sealed class AnalysisError : Exception() {

    object NotFound : AnalysisError() {
        private fun readResolve(): Any = NotFound
    }

    object InvalidConfig : AnalysisError() {
        private fun readResolve(): Any = InvalidConfig
    }

    object NoLeaves : AnalysisError() {
        private fun readResolve(): Any = InvalidConfig
    }

    object GPUNotSupportedByDevice : AnalysisError() {
        private fun readResolve(): Any = InvalidConfig
    }

}