package org.topit.rickmorty.ui.model

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.topit.rickmorty.domain.entities.Character

@Immutable
data class CharacterDetailUi(
    val id: Int,
    val name: String,
    val initials: String,
    val avatar: DrawableResource?,
    val status: StatusUi,
    val species: String,
    val type: String?,
    val gender: StringResource,
    val origin: String,
    val location: String,
    val episodeCount: String,
    val neighbours: List<CharacterCardUi>,
)

fun Character.toDetailUi(neighbours: List<Character>): CharacterDetailUi = CharacterDetailUi(
    id = id,
    name = name,
    initials = initialsOf(name),
    avatar = avatarOf(id),
    status = status.toUi(),
    species = species,
    type = type.ifBlank { null },
    gender = gender.label(),
    origin = origin.name,
    location = location.name,
    episodeCount = episode.size.toString(),
    neighbours = neighbours.toCardsUi(),
)
