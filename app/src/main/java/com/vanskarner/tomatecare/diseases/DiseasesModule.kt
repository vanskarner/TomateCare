package com.vanskarner.tomatecare.diseases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal object DiseasesModule {
    @Provides
    @FragmentScoped
    fun provideDiseaseDialog(): DiseaseDialog = DiseaseDialog()

    @Provides
    @FragmentScoped
    fun provideDiseaseAdapter(): DiseaseAdapter = DiseaseAdapter()
}