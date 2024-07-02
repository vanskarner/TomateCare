package com.vanskarner.tomatecare.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanskarner.analysis.persistence.ActivityLogDao
import com.vanskarner.analysis.persistence.ActivityLogEntity
import com.vanskarner.analysis.persistence.BoundingBoxesConverter
import com.vanskarner.analysis.persistence.ClassificationsConverter
import com.vanskarner.analysis.persistence.Converters

@Database(entities = [ActivityLogEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class, BoundingBoxesConverter::class, ClassificationsConverter::class])
abstract class AppRoomDB :RoomDatabase() {
    abstract fun activityLogDao(): ActivityLogDao
}