package org.topit.rickmorty.data

import org.topit.rickmorty.data.mappers.toDomain
import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.Episode
import org.topit.rickmorty.domain.entities.Location
import org.topit.rickmorty.domain.repositories.RickMortyRepository

class RickMortyRepositoryImpl : RickMortyRepository {
    override suspend fun getCharacters(): List<Character> = mockCharacters.map { it.toDomain() }

    override suspend fun getCharacter(id: Int): Character =
        mockCharacters.first { it.id == id }.toDomain()

    override suspend fun getEpisodes(): List<Episode> = mockEpisodes.map { it.toDomain() }

    override suspend fun getEpisode(id: Int): Episode =
        mockEpisodes.first { it.id == id }.toDomain()

    override suspend fun getLocations(): List<Location> = mockLocations.map { it.toDomain() }

    override suspend fun getLocation(id: Int): Location =
        mockLocations.first { it.id == id }.toDomain()
}
