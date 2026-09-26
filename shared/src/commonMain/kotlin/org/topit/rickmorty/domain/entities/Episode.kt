package org.topit.rickmorty.domain.entities

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val code: String,
    val characterIds: List<Int>,
    val created: String,
)
