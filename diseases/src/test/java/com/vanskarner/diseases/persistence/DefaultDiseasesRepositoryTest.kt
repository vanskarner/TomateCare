package com.vanskarner.diseases.persistence

import com.vanskarner.diseases.bussineslogic.DiseasesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
        val expectedTotalAmount = 8

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

    @Test(expected = DiseasesPersistenceError.NotFound::class)
    fun `find with invalid id should return NotFound`() = runTest {
        val invalidId = 0
        repository.find(invalidId).getOrThrow()
    }

}