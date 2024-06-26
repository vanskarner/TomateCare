package com.vanskarner.analysistracking.persistence

import com.vanskarner.analysistracking.AnalysisData
import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.bussineslogic.Repository

internal class DefaultRepository(
    private val activityLogDao: ActivityLogDao
) : Repository {

    override suspend fun list(): Result<List<AnalysisData>> {
        return Result.success(emptyList())
    }

    override suspend fun saveAnalysis(analysisDetailData: AnalysisDetailData): Result<Int> {
        return try {
            val id = activityLogDao.insert(analysisDetailData.toEntity())
            Result.success(id.toInt())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun findAnalysis(id: Int): Result<AnalysisDetailData> {
        return try {
            val item = activityLogDao.find(id)
            if (item == null) Result.failure(AnalysisError.NotFound)
            else Result.success(item.toData())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getAnalysisList(): Result<List<AnalysisDetailData>> {
        return try {
            val list = activityLogDao.toList()
                .map { it.toData() }
            Result.success(list)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit> {
        return try {
            val affectedRows = activityLogDao.updateNote(id, note)
            if (affectedRows == 0) Result.failure(AnalysisError.NotFound)
            else Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun deleteAnalysis(ids: List<Int>): Result<Int> {
        return try {
            val numberDeletedItems = activityLogDao.deleteByIds(ids)
            Result.success(numberDeletedItems)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}