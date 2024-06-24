package com.vanskarner.diseases.persistence

sealed class DiseasesPersistenceError : RuntimeException() {

    object NotFound : DiseasesPersistenceError() {
        private fun readResolve(): Any = NotFound
    }

}