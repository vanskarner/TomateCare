package com.vanskarner.analysistracking.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ActivityLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityLogEntity: ActivityLogEntity)

}