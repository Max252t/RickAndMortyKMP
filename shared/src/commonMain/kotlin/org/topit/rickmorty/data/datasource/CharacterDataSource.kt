package org.topit.rickmorty.data.datasource

import org.topit.rickmorty.data.dto.CharacterDto
import org.topit.rickmorty.data.dto.CharacterPageDto
import org.topit.rickmorty.data.dto.LocationDto

interface CharacterDataSource {
    suspend fun getCharacters(page: Int, name: String): CharacterPageDto
    suspend fun getCharacter(id: Int): CharacterDto
    suspend fun getLocation(id: Int): LocationDto
}
