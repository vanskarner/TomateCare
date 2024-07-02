package com.vanskarner.diseases.bussineslogic

import com.vanskarner.diseases.DiseaseData
import com.vanskarner.diseases.DiseaseDetailData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DiseasesUseCasesTest {
    private lateinit var repository: DiseasesRepository
    private lateinit var getDiseasesUseCase: GetDiseasesUseCase
    private lateinit var findDiseaseUseCase: FindDiseaseUseCase
    private lateinit var getNameByKeyCodeUseCase: GetNameByKeyCodeUseCase
    private lateinit var getNamesByKeyCodesUseCase: GetNamesByKeyCodesUseCase
    private lateinit var findDiseasesByKeyCodesUseCase: FindDiseasesByKeyCodesUseCase
    private lateinit var findDiseaseByKeyCodeUseCase: FindDiseaseByKeyCodeUseCase
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
    private val exampleName = "Bacterial Spot"
    private val exampleNameList = listOf("Bacterial Spot", "Mosaic Virus")
    private val exampleListByKeyCodes by lazy {
        listOf(
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
        )
    }

    @Before
    fun setUp() {
        repository = FakeDiseasesRepository(
            exampleList,
            exampleItem,
            exampleName,
            exampleNameList,
            exampleListByKeyCodes
        )
        getDiseasesUseCase = GetDiseasesUseCase(repository)
        findDiseaseUseCase = FindDiseaseUseCase(repository)
        getNameByKeyCodeUseCase = GetNameByKeyCodeUseCase(repository)
        getNamesByKeyCodesUseCase = GetNamesByKeyCodesUseCase(repository)
        findDiseasesByKeyCodesUseCase = FindDiseasesByKeyCodesUseCase(repository)
        findDiseaseByKeyCodeUseCase = FindDiseaseByKeyCodeUseCase(repository)
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

    @Test
    fun `execute getNameByKeyCodeUseCase return name`() = runTest {
        val actualName = getNameByKeyCodeUseCase.execute("any_code").getOrThrow()

        assertEquals(exampleName, actualName)
    }

    @Test
    fun `execute getNamesByKeyCodeUseCase return nameList`() = runTest {
        val keyCodes = listOf("any_code1", "any_code2")
        val actualNameList = getNamesByKeyCodesUseCase.execute(keyCodes).getOrThrow()

        assertEquals(exampleNameList.size, actualNameList.size)
    }

    @Test
    fun `execute findDiseasesByKeyCodesUseCase return diseases`() = runTest {
        val keyCodes = listOf("any_code1")
        val actualList = findDiseasesByKeyCodesUseCase.execute(keyCodes).getOrThrow()
        assertEquals(exampleListByKeyCodes.size, actualList.size)
    }

    @Test
    fun `execute findDiseaseByKeyCodeUseCase return disease`(): Unit = runTest {
        val exampleKeyCode = "bacterial_spot"
        val diseases = findDiseaseByKeyCodeUseCase.execute(exampleKeyCode).getOrThrow()

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