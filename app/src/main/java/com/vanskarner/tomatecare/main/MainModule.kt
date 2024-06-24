package com.vanskarner.tomatecare.main

import android.content.Context
import androidx.room.Room
import com.vanskarner.analysistracking.persistence.ActivityLogDao
import com.vanskarner.analysistracking.persistence.BoundingBoxesConverter
import com.vanskarner.analysistracking.persistence.ClassificationsConverter
import com.vanskarner.tomatecare.ui.CustomNavigationBottomNav
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
            .build();
    }

}