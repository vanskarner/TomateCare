package com.vanskarner.analysis.main

import android.content.Context
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.analysis.DefaultAnalysisComponent
import com.vanskarner.analysis.bussineslogic.AnalyzePlantUseCase
import com.vanskarner.analysis.bussineslogic.ClassifyLeavesUseCase
import com.vanskarner.analysis.bussineslogic.ComputerVision
import com.vanskarner.analysis.bussineslogic.DeleteAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.DetectLeavesUseCase
import com.vanskarner.analysis.bussineslogic.FindAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.GetAnalysisUseCase
import com.vanskarner.analysis.bussineslogic.GetConfigUseCase
import com.vanskarner.analysis.bussineslogic.Repository
import com.vanskarner.analysis.bussineslogic.UpdateAnalysisNoteUseCase
import com.vanskarner.analysis.bussineslogic.ValidateConfigUseCase
import com.vanskarner.analysis.computervision.TFLiteComputerVision
import com.vanskarner.analysis.persistence.ActivityLogDao
import com.vanskarner.analysis.persistence.DefaultRepository
import com.vanskarner.diseases.DiseasesComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AnalysisModule {

    @Provides
    fun providesComponent(
        getAnalysisUseCase: GetAnalysisUseCase,
        analysisPlantUseCase: AnalyzePlantUseCase,
        findAnalysisUseCase: FindAnalysisUseCase,
        getConfigUseCase: GetConfigUseCase,
        updateAnalysisNoteUseCase: UpdateAnalysisNoteUseCase,
        deleteAnalysisUseCase: DeleteAnalysisUseCase
    ): AnalysisComponent {
        return DefaultAnalysisComponent(
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
        return TFLiteComputerVision(context)
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