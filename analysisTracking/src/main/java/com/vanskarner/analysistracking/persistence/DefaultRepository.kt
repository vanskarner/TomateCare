package com.vanskarner.analysistracking.persistence

import com.vanskarner.analysistracking.AnalysisData
import com.vanskarner.analysistracking.AnalysisDetailData
import com.vanskarner.analysistracking.AnalysisError
import com.vanskarner.analysistracking.bussineslogic.Repository

internal class DefaultRepository(
    private val activityLogDao: ActivityLogDao
) : Repository {

    private var exampleData = mutableListOf(
        AnalysisData(
            1,
            true,
            "Ruta Imagen",
            12,
            "Nota 1",
            "24 abril 2024"
        ),
        AnalysisData(
            2,
            true,
            "Ruta Imagen",
            10,
            "Nota 2",
            "25 abril 2024"
        ),
        AnalysisData(
            3,
            false,
            "Ruta Imagen",
            5,
            "Nota 3",
            "26 abril 2024"
        ),
        AnalysisData(
            4,
            true,
            "Ruta Imagen",
            10,
            "Nota 4",
            "27 abril 2024"
        ),
        AnalysisData(
            5,
            true,
            "Ruta Imagen",
            10,
            "Nota 5",
            "28 abril 2024"
        ),
        AnalysisData(
            6,
            true,
            "Ruta Imagen",
            10,
            "Nota 6",
            "29 abril 2024"
        ),
        AnalysisData(
            7,
            true,
            "Ruta Imagen",
            10,
            "Nota 7",
            "30 abril 2024"
        ),
        AnalysisData(
            8,
            false,
            "Ruta Imagen",
            10,
            "Nota 8",
            "31 abril 2024"
        ),
        AnalysisData(
            9,
            false,
            "Ruta Imagen",
            2,
            "Nota 9",
            "01 mayo 2024"
        ),
        AnalysisData(
            10,
            false,
            "Ruta Imagen",
            1,
            "Nota 10",
            "02 mayo 2024"
        ),
    )

    override suspend fun list(): Result<List<AnalysisData>> {
        return Result.success(exampleData)
    }

    override suspend fun saveAnalysis(analysisDetailData: AnalysisDetailData): Result<Unit> {
        return try {
            activityLogDao.insert(analysisDetailData.toEntity())
            Result.success(Unit)
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

    override suspend fun deleteAnalysis(ids: List<Int>): Result<Unit> {
        return try {
            activityLogDao.deleteByIds(ids)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}