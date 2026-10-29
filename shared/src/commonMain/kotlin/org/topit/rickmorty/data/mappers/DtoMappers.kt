package org.topit.rickmorty.data.mappers

import org.topit.rickmorty.data.dto.CharacterDto
import org.topit.rickmorty.data.dto.EpisodeDto
import org.topit.rickmorty.data.dto.LocationDto
import org.topit.rickmorty.data.dto.LocationRefDto
import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.Episode
import org.topit.rickmorty.domain.entities.Gender
import org.topit.rickmorty.domain.entities.Location
import org.topit.rickmorty.domain.entities.LocationRef
import org.topit.rickmorty.domain.entities.Status

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status.toStatus(),
    species = species,
    type = type,
    gender = gender.toGender(),
    origin = origin.toDomain(),
    location = location.toDomain(),
    imageUrl = image,
    episodeIds = episode.mapNotNull(::idFromUrl),
    created = created,
)

fun EpisodeDto.toDomain(): Episode = Episode(
    id = id,
    name = name,
    airDate = airDate,
    code = episode,
    characterIds = characters.mapNotNull(::idFromUrl),
    created = created,
)

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentIds = residents.mapNotNull(::idFromUrl),
    created = created,
)

private fun LocationRefDto.toDomain(): LocationRef = LocationRef(
    id = idFromUrl(url),
    name = name,
)

private fun idFromUrl(url: String): Int? = url.substringAfterLast('/').toIntOrNull()

private fun String.toStatus(): Status = when (lowercase()) {
    "alive" -> Status.ALIVE
    "dead" -> Status.DEAD
    else -> Status.UNKNOWN
}

private fun String.toGender(): Gender = when (lowercase()) {
    "female" -> Gender.FEMALE
    "male" -> Gender.MALE
    "genderless" -> Gender.GENDERLESS
    else -> Gender.UNKNOWN
}
