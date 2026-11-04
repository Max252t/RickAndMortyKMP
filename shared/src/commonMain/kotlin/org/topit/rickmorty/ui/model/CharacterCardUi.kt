package org.topit.rickmorty.ui.model

import androidx.compose.runtime.Immutable
import org.topit.rickmorty.domain.entities.Character

@Immutable
data class CharacterCardUi(
    val id: Int,
    val name: String,
    val initials: String,
    val imageUrl: String,
    val species: String,
    val status: StatusUi,
)

fun Character.toCardUi(): CharacterCardUi = CharacterCardUi(
    id = id,
    name = name,
    initials = initialsOf(name),
    imageUrl = imageUrl,
    species = species,
    status = status.toUi(),
)

fun List<Character>.toCardsUi(): List<CharacterCardUi> = map { it.toCardUi() }

fun initialsOf(name: String): String =
    name.split(' ')
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
