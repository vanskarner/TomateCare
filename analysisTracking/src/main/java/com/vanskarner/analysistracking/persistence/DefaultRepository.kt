package com.vanskarner.analysistracking.persistence

import com.vanskarner.analysistracking.AnalysisData
import com.vanskarner.analysistracking.bussineslogic.Repository

internal class DefaultRepository :Repository{

    private var exampleData = mutableListOf(
        AnalysisData(
            1,
            false,
            "Ruta Imagen",
            "Nota 1",
            "24 abril 2024"
        ),
        AnalysisData(
            2,
            false,
            "Ruta Imagen",
            "Nota 2",
            "24 abril 2024"
        ),
        AnalysisData(
            3,
            false,
            "Ruta Imagen",
            "Nota 3",
            "24 abril 2024"
        ),
        AnalysisData(
            4,
            false,
            "Ruta Imagen",
            "Nota 4",
            "24 abril 2024"
        ),
        AnalysisData(
            5,
            false,
            "Ruta Imagen",
            "Nota 5",
            "24 abril 2024"
        ),
        AnalysisData(
            6,
            false,
            "Ruta Imagen",
            "Nota 6",
            "24 abril 2024"
        ),
        AnalysisData(
            7,
            false,
            "Ruta Imagen",
            "Nota 7",
            "24 abril 2024"
        ),
        AnalysisData(
            8,
            false,
            "Ruta Imagen",
            "Nota 8",
            "24 abril 2024"
        ),
        AnalysisData(
            9,
            false,
            "Ruta Imagen",
            "Nota 9",
            "24 abril 2024"
        ),
        AnalysisData(
            10,
            false,
            "Ruta Imagen",
            "Nota 10",
            "24 abril 2024"
        )
    )

    override suspend fun list(): Result<List<AnalysisData>> {
        return Result.success(exampleData)
    }

}