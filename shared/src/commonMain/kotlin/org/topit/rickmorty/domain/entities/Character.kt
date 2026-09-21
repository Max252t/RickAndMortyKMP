package org.topit.rickmorty.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: LocationRef,
    val location: LocationRef,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Serializable
data class LocationRef(
    val name: String,
    val url: String
)

@Serializable
enum class Status {
    @SerialName("Alive") ALIVE,
    @SerialName("Dead") DEAD,
    @SerialName("unknown") UNKNOWN
}

@Serializable
enum class Gender {
    @SerialName("Female") FEMALE,
    @SerialName("Male") MALE,
    @SerialName("Genderless") GENDERLESS,
    @SerialName("unknown") UNKNOWN
}