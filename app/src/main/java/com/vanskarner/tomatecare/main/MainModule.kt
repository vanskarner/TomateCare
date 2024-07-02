package com.vanskarner.tomatecare.main

import android.content.Context
import androidx.room.Room
import com.vanskarner.analysis.persistence.ActivityLogDao
import com.vanskarner.analysis.persistence.BoundingBoxesConverter
import com.vanskarner.analysis.persistence.ClassificationsConverter
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.diseases.main.DiseasesComponentFactory
import com.vanskarner.tomatecare.ui.CustomNavigationBottomNav
import com.vanskarner.tomatecare.ui.errors.DefaultErrorFilter
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainActivityModule {

    @Provides
    @Singleton
    fun provideCustomNavBottom(): CustomNavigationBottomNav = CustomNavigationBottomNav()

    @Provides
    @Singleton
    fun provideMovieDetailDao(db: AppRoomDB): ActivityLogDao {
        return db.activityLogDao()
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): AppRoomDB {
        return Room.databaseBuilder(context, AppRoomDB::class.java, "TomateCareDB")
            .addTypeConverter(BoundingBoxesConverter())
            .addTypeConverter(ClassificationsConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDiseasesComponent(): DiseasesComponent = DiseasesComponentFactory.createComponent()

    @Provides
    @Singleton
    fun provideErrorFilter(@ApplicationContext context: Context): ErrorFilter {
        return DefaultErrorFilter(context)
    }

}