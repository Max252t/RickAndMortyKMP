package org.topit.rickmorty.domain.repositories

import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.CharacterPage

interface RickMortyRepository {
    suspend fun getCharacters(page: Int, name: String = ""): CharacterPage
    suspend fun getCharacter(id: Int): Character
    suspend fun getNeighbours(character: Character): List<Character>
}
