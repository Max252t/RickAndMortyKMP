package org.topit.rickmorty.domain.repositories

import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.Episode
import org.topit.rickmorty.domain.entities.Location

interface RickMortyRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun getCharacter(id: Int): Character
    suspend fun getEpisodes(): List<Episode>
    suspend fun getEpisode(id: Int): Episode
    suspend fun getLocations(): List<Location>
    suspend fun getLocation(id: Int): Location
}

suspend fun RickMortyRepository.getCharacters(filter: String?): List<Character> =
    getCharacters().filterByName(filter)

suspend fun RickMortyRepository.getNeighbours(character: Character): List<Character> {
    if (character.location.url.isEmpty()) return emptyList()
    return getCharacters().filter { it.location.url == character.location.url && it.id != character.id }
}

fun List<Character>.filterByName(filter: String?): List<Character> {
    val needle = filter?.trim().orEmpty()
    if (needle.isEmpty()) return this
    return filter { it.name.contains(needle, ignoreCase = true) }
}
