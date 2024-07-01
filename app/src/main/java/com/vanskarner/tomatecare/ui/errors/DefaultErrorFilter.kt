package com.vanskarner.tomatecare.ui.errors

import android.content.Context
import com.vanskarner.analysis.AnalysisError
import com.vanskarner.diseases.persistence.DiseasesPersistenceError
import com.vanskarner.tomatecare.R

internal class DefaultErrorFilter(
    private val context: Context
) : ErrorFilter {

    override fun filter(throwable: Throwable): String {
        return when (throwable) {
            is AnalysisError -> filterAnalysisError(throwable)
            is DiseasesPersistenceError -> filterDiseasesError(throwable)
            else -> getString(R.string.error_unknown)
        }
    }

    private fun filterAnalysisError(error: AnalysisError) = when (error) {
        AnalysisError.GPUNotSupportedByDevice -> getString(R.string.error_gpu_not_supported_device)
        AnalysisError.InvalidConfig -> getString(R.string.error_invalid_config)
        AnalysisError.NoLeaves -> getString(R.string.error_no_leaves)
        AnalysisError.NotFound -> getString(R.string.error_no_found)
        AnalysisError.InvalidModel -> getString(R.string.error_invalid_model)
    }

    private fun filterDiseasesError(error: DiseasesPersistenceError) = when (error) {
        DiseasesPersistenceError.NotFound -> getString(R.string.error_disease_not_found)
    }

    private fun getString(stringId: Int) = context.getString(stringId)
}
