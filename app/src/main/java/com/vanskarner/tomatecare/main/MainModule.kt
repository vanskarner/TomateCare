package com.vanskarner.tomatecare.main

import com.vanskarner.tomatecare.ui.CustomNavigationBottomNav
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainActivityModule {

    @Provides
    @Singleton
    fun provideCustomNavBottom(): CustomNavigationBottomNav = CustomNavigationBottomNav()

}