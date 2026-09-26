package org.topit.rickmorty.domain.entities

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: LocationRef,
    val location: LocationRef,
    val imageUrl: String,
    val episodeIds: List<Int>,
    val created: String,
)

data class LocationRef(
    val id: Int?,
    val name: String,
)

enum class Status {
    ALIVE,
    DEAD,
    UNKNOWN,
}

enum class Gender {
    FEMALE,
    MALE,
    GENDERLESS,
    UNKNOWN,
}
