package com.vanskarner.diseases.persistence

import com.vanskarner.diseases.DiseaseDetailData
import com.vanskarner.diseases.DiseasesError
import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DefaultDiseasesRepositoryTest {
    private lateinit var repository: DiseasesRepository

    @Before
    fun setup() {
        repository = DefaultDiseasesRepository()
    }

    @Test
    fun `list return all diseases`() = runTest {
        val diseases = repository.list().getOrThrow()
        val actualTotalAmount = diseases.size
        val expectedTotalAmount = 9

        assertEquals(expectedTotalAmount, actualTotalAmount)
        diseases.forEach {
            assertTrue(it.name.isNotEmpty())
            assertTrue(it.imageBase64.isNotEmpty())
            assertTrue(it.symptoms.isNotEmpty())
        }
    }

    @Test
    fun `find with valid id should get disease`() = runTest {
        val actualId = 1
        val detailDisease = repository.find(actualId).getOrThrow()

        assertEquals(actualId, detailDisease.id)
        assertTrue(detailDisease.name.isNotEmpty())
        assertTrue(detailDisease.imageBase64.isNotEmpty())
        assertTrue(detailDisease.symptoms.isNotEmpty())
    }

    @Test(expected = DiseasesError.NotFound::class)
    fun `find with invalid id should return NotFound`() = runTest {
        val invalidId = 0
        repository.find(invalidId).getOrThrow()
    }

    @Test
    fun `getNameByKeyCode with invalid keyCode should return empty string`() = runTest {
        val name = repository.getNameByKeyCode("unknown").getOrThrow()

        assertTrue(name.isEmpty())
    }

    @Test
    fun `getNameByKeyCode with valid keyCode should return name`() = runTest {
        val name = repository.getNameByKeyCode("bacterial_spot").getOrThrow()

        assertEquals("Mancha bacteriana", name)
    }

    @Test
    fun `getNamesByKeyCodes with valid keyCodes should return names`() = runTest {
        val ids = listOf(
            "bacterial_spot",
            "early_blight",
            "late_blight",
            "leaf_mold",
            "mosaic_virus",
            "septoria_leaf_spot",
            "target_spot",
            "yellow_leaf_curl_virus",
            "twospotted_spider_mite"
        )
        val names = repository.getNamesByKeyCodes(ids).getOrThrow()

        assertEquals("Mancha bacteriana", names[0])
        assertEquals("Tizón temprano", names[1])
        assertEquals("Tizón tardío", names[2])
        assertEquals("Moho de las hojas", names[3])
        assertEquals("Virus del mosaico", names[4])
        assertEquals("Mancha foliar por septoria", names[5])
        assertEquals("Mancha diana", names[6])
        assertEquals("Virus del enrollamiento de la hoja amarilla", names[7])
        assertEquals("Araña roja de dos manchas", names[8])
    }

    @Test
    fun `getNamesByKeyCodes with invalid keyCodes should return some empty names`() = runTest {
        val ids = listOf(
            "unknown",
            "early_blight",
            "late_blight",
            "leaf_mold",
            "unknown",
            "septoria_leaf_spot",
            "target_spot",
            "yellow_leaf_curl_virus",
            "unknown"
        )
        val names = repository.getNamesByKeyCodes(ids).getOrThrow()

        assertEquals("", names[0])
        assertEquals("Tizón temprano", names[1])
        assertEquals("Tizón tardío", names[2])
        assertEquals("Moho de las hojas", names[3])
        assertEquals("", names[4])
        assertEquals("Mancha foliar por septoria", names[5])
        assertEquals("Mancha diana", names[6])
        assertEquals("Virus del enrollamiento de la hoja amarilla", names[7])
        assertEquals("", names[8])
    }

    @Test
    fun `findByKeyCodes with invalid keyCodes should return some empty diseases`() = runTest {
        val emptyDisease = DiseaseDetailData.empty()
        val keyCodes = listOf(
            "unknown",
            "early_blight",
            "unknown"
        )
        val diseases = repository.findByKeyCodes(keyCodes).getOrThrow()

        assertEquals(keyCodes.size,diseases.size)
        assertEquals(emptyDisease, diseases[0])
        assertNotEquals(emptyDisease, diseases[1])
        assertEquals(emptyDisease, diseases[2])
    }

    @Test
    fun `findByKeyCode with valid id should get disease`() = runTest {
        val exampleKeyCode = "bacterial_spot"
        val detailDisease = repository.findByKeyCode(exampleKeyCode).getOrThrow()

        assertEquals(1, detailDisease.id)
        assertTrue(detailDisease.name.isNotEmpty())
        assertTrue(detailDisease.imageBase64.isNotEmpty())
        assertTrue(detailDisease.symptoms.isNotEmpty())
    }

    @Test(expected = DiseasesError.NotFound::class)
    fun `find findByKeyCode invalid id should return NotFound`() = runTest {
        val exampleKeyCode = "unknown"
        repository.findByKeyCode(exampleKeyCode).getOrThrow()
    }
}