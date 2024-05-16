package com.vanskarner.tomatecare.ui.capture

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal object CaptureModule {

    @Provides
    @FragmentScoped
    fun provideSettingDialog(): SettingDialog = SettingDialog()

    @Provides
    @FragmentScoped
    fun provideAdvicesDialog(): AdvicesDialog = AdvicesDialog()

}