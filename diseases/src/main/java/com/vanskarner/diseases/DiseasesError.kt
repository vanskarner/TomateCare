package com.vanskarner.diseases

sealed class DiseasesError : RuntimeException() {

    object NotFound : DiseasesError() {
        private fun readResolve(): Any = NotFound
    }

}