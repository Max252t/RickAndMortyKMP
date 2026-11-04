package org.topit.rickmorty.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPageDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>,
)

@Serializable
data class PageInfoDto(
    val next: String? = null,
)
