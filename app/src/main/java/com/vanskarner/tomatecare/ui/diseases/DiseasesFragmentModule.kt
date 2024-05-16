package com.vanskarner.tomatecare.ui.diseases

import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.main.DiseasesComponentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped

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

@Module
@InstallIn(ViewModelComponent::class)
internal object DiseasesViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideDiseasesComponent(): DiseasesComponent = DiseasesComponentFactory.createComponent()

}
