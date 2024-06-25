package com.vanskarner.analysistracking.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivityLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityLogEntity: ActivityLogEntity): Long

    @Query("SELECT * FROM activity_log WHERE uid=:id LIMIT 1")
    suspend fun find(id: Int): ActivityLogEntity?

    @Query("SELECT * FROM activity_log")
    suspend fun toList(): List<ActivityLogEntity>

    @Query("UPDATE activity_log SET note = :updatedNote WHERE uid = :id")
    suspend fun updateNote(id: Int, updatedNote: String): Int

    @Query("DELETE FROM activity_log WHERE uid IN (:userIds)")
    suspend fun deleteByIds(userIds: List<Int>)

}
