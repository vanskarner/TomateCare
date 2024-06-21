package com.vanskarner.analysistracking.main

import com.vanskarner.analysistracking.AnalysisTrackingComponent
import com.vanskarner.analysistracking.DefaultAnalysisTrackingComponent
import com.vanskarner.analysistracking.bussineslogic.GetAnalysisTrackingUseCases
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
    fun providesComponent(getAnalysisTrackingUseCases: GetAnalysisTrackingUseCases)
            : AnalysisTrackingComponent {
        return DefaultAnalysisTrackingComponent(getAnalysisTrackingUseCases)
    }

    @Provides
    fun providesRepository(): Repository {
        return DefaultRepository()
    }

    @Provides
    fun provideGetAnalysisTracking(repository: Repository): GetAnalysisTrackingUseCases {
        return GetAnalysisTrackingUseCases(repository)
    }

}