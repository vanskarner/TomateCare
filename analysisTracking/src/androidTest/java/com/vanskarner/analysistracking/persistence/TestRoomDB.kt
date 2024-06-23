package com.vanskarner.analysistracking.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ActivityLogEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TestRoomDB : RoomDatabase() {
    abstract fun activityLogDao(): ActivityLogDao
}