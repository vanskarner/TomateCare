package com.vanskarner.tomatecare.ui.identification

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal object IdentificationModule {

    @Provides
    @FragmentScoped
    fun provideLeafAdapter():LeafAdapter = LeafAdapter()

    @Provides
    @FragmentScoped
    fun provideRecommendationsAdapter():RecommendationsAdapter = RecommendationsAdapter()

}