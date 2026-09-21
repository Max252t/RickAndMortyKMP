package org.topit.rickmorty.data

import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.Episode
import org.topit.rickmorty.domain.entities.Location
import org.topit.rickmorty.domain.repositories.RickMortyRepository

class RickMortyRepositoryImpl : RickMortyRepository {
    override suspend fun getCharacters(): List<Character> = mockCharacters

    override suspend fun getCharacter(id: Int): Character = mockCharacters.first { it.id == id }

    override suspend fun getEpisodes(): List<Episode> = mockEpisodes

    override suspend fun getEpisode(id: Int): Episode = mockEpisodes.first { it.id == id }

    override suspend fun getLocations(): List<Location> = mockLocations

    override suspend fun getLocation(id: Int): Location = mockLocations.first { it.id == id }
}
