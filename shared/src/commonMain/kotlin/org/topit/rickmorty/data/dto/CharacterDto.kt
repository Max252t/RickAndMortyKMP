package org.topit.rickmorty.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationRefDto,
    val location: LocationRefDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
)

@Serializable
data class LocationRefDto(
    val name: String,
    val url: String,
)
