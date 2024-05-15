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
    suspend fun find(diseaseId:Int): Result<DiseaseDetailData>

}