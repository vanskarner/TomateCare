package com.vanskarner.tomatecare

import com.vanskarner.tomatecare.activitylogs.LogsAdapter
import com.vanskarner.tomatecare.capture.AdvicesDialog
import com.vanskarner.tomatecare.capture.SettingDialog
import com.vanskarner.tomatecare.diseases.DiseaseAdapter
import com.vanskarner.tomatecare.diseases.DiseaseDialog
import com.vanskarner.tomatecare.identification.LeafAdapter
import com.vanskarner.tomatecare.identification.RecommendationsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenericModule {

    @Provides
    @Singleton
    fun provideCustomNavBottom(): CustomNavigationBottomNav = CustomNavigationBottomNav()

    @Provides
    fun provideLeafAdapter():LeafAdapter = LeafAdapter() //identification

    @Provides
    fun provideRecommendationsAdapter():RecommendationsAdapter = RecommendationsAdapter() //identification

    @Provides
    fun provideDiseaseDialog(): DiseaseDialog = DiseaseDialog() //diseases

    @Provides
    fun provideDiseaseAdapter(): DiseaseAdapter = DiseaseAdapter() //diseases

    @Provides
    fun provideSettingDialog(): SettingDialog = SettingDialog() //capture

    @Provides
    fun provideAdvicesDialog(): AdvicesDialog = AdvicesDialog() //capture

    @Provides
    fun provideLogsAdapter(): LogsAdapter = LogsAdapter() //activitylogs

}