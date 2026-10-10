package org.topit.rickmorty.ui.model

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.domain.repositories.getCharacters

@Immutable
data class CharacterCardUi(
    val id: Int,
    val name: String,
    val initials: String,
    val avatar: DrawableResource?,
    val species: String,
    val status: StatusUi,
)

fun Character.toCardUi(): CharacterCardUi = CharacterCardUi(
    id = id,
    name = name,
    initials = initialsOf(name),
    avatar = avatarOf(id),
    species = species,
    status = status.toUi(),
)

fun List<Character>.toCardsUi(): List<CharacterCardUi> = map { it.toCardUi() }

suspend fun RickMortyRepository.getCards(filter: String?): List<CharacterCardUi> =
    getCharacters(filter).toCardsUi()

fun initialsOf(name: String): String =
    name.split(' ')
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
