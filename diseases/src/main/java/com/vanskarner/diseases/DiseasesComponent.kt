package com.vanskarner.diseases

import com.vanskarner.diseases.bussineslogic.DiseaseData
import com.vanskarner.diseases.bussineslogic.DiseaseDetailData
import com.vanskarner.diseases.persistence.DiseasesPersistenceError

/**
 * Proporciona informacion sobre las enfermedades que se identificaran
 * @author Luis H. Olazo
 * @version 1.0.0
 */
interface DiseasesComponent {

    /**
     * Obtains information on all diseases
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the list of diseases
     * - [Result.onFailure], does not generate any type of error
     */
    suspend fun getList(): Result<List<DiseaseData>>

    /**
     * Find the disease by its ID.
     * @param diseaseId disease identifier
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the searched [DiseaseDetailData]
     * - [Result.onFailure], one of the following errors occurred:
     * [DiseasesPersistenceError.NotFound]
     */
    suspend fun find(diseaseId: Int): Result<DiseaseDetailData>

    /**
     * Gets the name of the disease with the keyCode, if the code is
     * invalid an empty string is returned
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns name
     * - [Result.onFailure], does not generate any type of error
     */
    suspend fun getNameByKeyCode(keyCode: String): Result<String>

    /**
     * Gets the names of the diseases with the specified key codes, if there are
     * invalid codes an empty string is returned instead.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns list of names
     * - [Result.onFailure], does not generate any type of error
     */
    suspend fun getNamesByKeyCodes(keyCodes: List<String>): Result<List<String>>

    /**
     * Gets the diseases with the specified key codes, if there are
     * invalid codes an empty disease is returned instead.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns diseases
     * - [Result.onFailure], does not generate any type of error
     */
    suspend fun findByKeyCodes(keyCodes: List<String>): Result<List<DiseaseDetailData>>

}