package com.vanskarner.analysistracking.main

import android.content.Context
import com.vanskarner.analysistracking.AnalysisTrackingComponent
import com.vanskarner.analysistracking.DefaultAnalysisTrackingComponent
import com.vanskarner.analysistracking.bussineslogic.AnalyzePlantUseCase
import com.vanskarner.analysistracking.bussineslogic.ClassifyLeavesUseCase
import com.vanskarner.analysistracking.bussineslogic.ComputerVision
import com.vanskarner.analysistracking.bussineslogic.DeleteAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.DetectLeavesUseCase
import com.vanskarner.analysistracking.bussineslogic.FindAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.GetAnalysisUseCase
import com.vanskarner.analysistracking.bussineslogic.GetConfigUseCase
import com.vanskarner.analysistracking.bussineslogic.Repository
import com.vanskarner.analysistracking.bussineslogic.UpdateAnalysisNoteUseCase
import com.vanskarner.analysistracking.bussineslogic.ValidateConfigUseCase
import com.vanskarner.analysistracking.computervision.DefaultComputerVision
import com.vanskarner.analysistracking.persistence.ActivityLogDao
import com.vanskarner.analysistracking.persistence.DefaultRepository
import com.vanskarner.diseases.DiseasesComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AnalysisTrackingModule {

    @Provides
    fun providesComponent(
        getAnalysisUseCase: GetAnalysisUseCase,
        analysisPlantUseCase: AnalyzePlantUseCase,
        findAnalysisUseCase: FindAnalysisUseCase,
        getConfigUseCase: GetConfigUseCase,
        updateAnalysisNoteUseCase: UpdateAnalysisNoteUseCase,
        deleteAnalysisUseCase: DeleteAnalysisUseCase
    ): AnalysisTrackingComponent {
        return DefaultAnalysisTrackingComponent(
            getAnalysisUseCase,
            analysisPlantUseCase,
            findAnalysisUseCase,
            getConfigUseCase,
            updateAnalysisNoteUseCase,
            deleteAnalysisUseCase
        )
    }

    @Provides
    fun providesRepository(activityLogDao: ActivityLogDao): Repository {
        return DefaultRepository(activityLogDao)
    }

    @Provides
    fun providesComputerVision(@ApplicationContext context: Context): ComputerVision {
        return DefaultComputerVision(context)
    }

    @Provides
    fun provideGetAnalysisUseCase(repository: Repository): GetAnalysisUseCase {
        return GetAnalysisUseCase(repository)
    }

    @Provides
    fun provideAnalyzePlantUseCase(
        repository: Repository,
        computerVision: ComputerVision
    ): AnalyzePlantUseCase {
        val validateConfigUseCase = ValidateConfigUseCase()
        return AnalyzePlantUseCase(
            validateConfigUseCase,
            DetectLeavesUseCase(computerVision),
            ClassifyLeavesUseCase(validateConfigUseCase, computerVision),
            repository
        )
    }

    @Provides
    fun provideFindAnalysisUseCase(
        repository: Repository,
        diseasesComponent: DiseasesComponent
    ): FindAnalysisUseCase {
        return FindAnalysisUseCase(repository, diseasesComponent)
    }

    @Provides
    fun provideGetConfigUseCase(): GetConfigUseCase {
        return GetConfigUseCase()
    }

    @Provides
    fun provideUpdateAnalysisNoteUseCase(repository: Repository): UpdateAnalysisNoteUseCase {
        return UpdateAnalysisNoteUseCase(repository)
    }

    @Provides
    fun provideDeleteAnalysisUseCase(repository: Repository): DeleteAnalysisUseCase {
        return DeleteAnalysisUseCase(repository)
    }

}