package com.vanskarner.diseases.persistence

import org.junit.Assert.assertEquals
import org.junit.Test

class DiseasesMapperTest {

    @Test
    fun `toData from DiseaseDTO`() {
        val expected = DiseaseDTO(
            id = 1,
            name = "Bacterial Spot",
            imageBase64 = "any",
            causalAgent = "Some agent",
            symptoms = "Some symptoms",
            developmentConditions = "Some condition",
            control = "Some control",
            source = "Some source"
        )
        val actual = expected.toData()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.imageBase64, actual.imageBase64)
        assertEquals(expected.symptoms, actual.symptoms)
    }

    @Test
    fun `toDetailData from DiseaseDTO`() {
        val expected = DiseaseDTO(
            id = 1,
            name = "Bacterial Spot",
            imageBase64 = "any",
            causalAgent = "Some agent",
            symptoms = "Some symptoms",
            developmentConditions = "Some condition",
            control = "Some control",
            source = "Some source"
        )
        val actual = expected.toDetailData()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.imageBase64, actual.imageBase64)
        assertEquals(expected.causalAgent, actual.causalAgent)
        assertEquals(expected.symptoms, actual.symptoms)
        assertEquals(expected.developmentConditions, actual.developmentConditions)
        assertEquals(expected.control, actual.control)
        assertEquals(expected.source, actual.source)
    }

    @Test
    fun `toListData from DiseasesDTO`() {
        val expected = DiseasesDTO(
            listOf(
                DiseaseDTO(
                    id = 1,
                    name = "Bacterial Spot",
                    imageBase64 = "any",
                    causalAgent = "Some agent",
                    symptoms = "Some symptoms",
                    developmentConditions = "Some condition",
                    control = "Some control",
                    source = "Some source"
                )
            )
        )
        val actual = expected.toListData()

        assertEquals(expected.diseases.size, actual.size)
        assertEquals(expected.diseases[0].id, actual[0].id)
        assertEquals(expected.diseases[0].name, actual[0].name)
        assertEquals(expected.diseases[0].imageBase64, actual[0].imageBase64)
        assertEquals(expected.diseases[0].symptoms, actual[0].symptoms)
    }

}