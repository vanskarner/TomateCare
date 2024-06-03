package com.vanskarner.analysistracking.main

import com.vanskarner.analysistracking.AnalysisTrackingComponent
import com.vanskarner.analysistracking.DefaultAnalysisTrackingComponent
import com.vanskarner.analysistracking.bussineslogic.GetAnalysisTracking
import com.vanskarner.analysistracking.bussineslogic.Repository
import com.vanskarner.analysistracking.persistence.DefaultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AnalysisTrackingModule {

    @Provides
    fun providesComponent(getAnalysisTracking: GetAnalysisTracking): AnalysisTrackingComponent {
        return DefaultAnalysisTrackingComponent(getAnalysisTracking)
    }

    @Provides
    fun providesRepository(): Repository {
        return DefaultRepository()
    }

    @Provides
    fun provideUse(repository: Repository): GetAnalysisTracking {
        return GetAnalysisTracking(repository)
    }

}