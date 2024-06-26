package com.vanskarner.tomatecare.ui.diseases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal object DiseasesFragmentModule {

    @Provides
    @FragmentScoped
    fun provideDiseaseDialog(): DiseaseDialog = DiseaseDialog()

    @Provides
    @FragmentScoped
    fun provideDiseaseAdapter(): DiseaseAdapter = DiseaseAdapter()

}
