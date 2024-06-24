package com.vanskarner.tomatecare.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanskarner.analysistracking.persistence.ActivityLogDao
import com.vanskarner.analysistracking.persistence.ActivityLogEntity
import com.vanskarner.analysistracking.persistence.BoundingBoxesConverter
import com.vanskarner.analysistracking.persistence.ClassificationsConverter
import com.vanskarner.analysistracking.persistence.Converters

@Database(entities = [ActivityLogEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class, BoundingBoxesConverter::class, ClassificationsConverter::class])
abstract class AppRoomDB :RoomDatabase() {
    abstract fun activityLogDao(): ActivityLogDao
}