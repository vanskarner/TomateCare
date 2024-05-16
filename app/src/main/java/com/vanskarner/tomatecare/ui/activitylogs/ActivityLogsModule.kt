package com.vanskarner.tomatecare.ui.activitylogs

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
internal object ActivityLogsModule {

    @Provides
    fun provideLogsAdapter(): LogsAdapter = LogsAdapter()

}