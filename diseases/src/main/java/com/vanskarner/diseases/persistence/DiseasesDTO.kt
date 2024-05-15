package com.vanskarner.diseases.persistence

import kotlinx.serialization.*

@Serializable
internal data class DiseasesDTO(
    @SerialName("diseases")
    val diseases: List<DiseaseDTO>
)

@Serializable
internal data class DiseaseDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image_base64")
    val imageBase64: String,
    @SerialName("causal_agents")
    val causalAgent: String,
    @SerialName("symptoms")
    val symptoms: String,
    @SerialName("development_conditions")
    val developmentConditions: String,
    @SerialName("control")
    val control: String,
    @SerialName("source")
    val source: String
)
