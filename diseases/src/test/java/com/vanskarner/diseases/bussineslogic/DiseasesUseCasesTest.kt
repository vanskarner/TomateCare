package com.vanskarner.diseases.bussineslogic

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DiseasesUseCasesTest {
    private lateinit var repository: DiseasesRepository
    private lateinit var getDiseasesUseCase: GetDiseasesUseCase
    private lateinit var findDiseaseUseCase: FindDiseaseUseCase
    private val exampleList by lazy {
        listOf(
            DiseaseData(1, "Bacterial Spot", "Some Image", "Some Symptoms"),
            DiseaseData(2, "Leaf Mold", "Some Image", "Some Symptoms"),
        )
    }
    private val exampleItem by lazy {
        DiseaseDetailData(
            1,
            "Bacterial Spot",
            "Some Image",
            "Some Agent",
            "Some Symptoms",
            "Some Condition",
            "Some Control",
            "Some Source"
        )
    }

    @Before
    fun setUp() {
        repository = FakeDiseasesRepository(exampleList, exampleItem)
        getDiseasesUseCase = GetDiseasesUseCase(repository)
        findDiseaseUseCase = FindDiseaseUseCase(repository)
    }

    @Test
    fun `execute getDiseasesUseCase should return diseases`(): Unit = runTest {
        val diseases = getDiseasesUseCase.execute().getOrThrow()

        assertEquals(exampleList.size, diseases.size)
    }

    @Test
    fun `execute findDiseaseUseCase return disease`(): Unit = runTest {
        val diseases = findDiseaseUseCase.execute(exampleItem.id).getOrThrow()

        assertEquals(exampleItem.id, diseases.id)
        assertEquals(exampleItem.name, diseases.name)
        assertEquals(exampleItem.imageBase64, diseases.imageBase64)
        assertEquals(exampleItem.causalAgent, diseases.causalAgent)
        assertEquals(exampleItem.symptoms, diseases.symptoms)
        assertEquals(exampleItem.developmentConditions, diseases.developmentConditions)
        assertEquals(exampleItem.control, diseases.control)
        assertEquals(exampleItem.source, diseases.source)
    }

}