package com.vanskarner.diseases.persistence

sealed class DiseasesPersistenceError : RuntimeException() {

    object NotFound : DiseasesPersistenceError()

}