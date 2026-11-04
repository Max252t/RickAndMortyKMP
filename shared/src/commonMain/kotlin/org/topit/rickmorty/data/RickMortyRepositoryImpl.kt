package org.topit.rickmorty.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.topit.rickmorty.data.datasource.CharacterDataSource
import org.topit.rickmorty.data.mappers.residentIds
import org.topit.rickmorty.data.mappers.toDomain
import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.entities.CharacterPage
import org.topit.rickmorty.domain.errors.LoadException
import org.topit.rickmorty.domain.repositories.RickMortyRepository

private const val NEIGHBOURS_LIMIT = 20

class RickMortyRepositoryImpl(
    private val dataSource: CharacterDataSource,
) : RickMortyRepository {

    override suspend fun getCharacters(page: Int, name: String): CharacterPage = try {
        dataSource.getCharacters(page, name.trim()).toDomain()
    } catch (_: LoadException.NotFound) {
        CharacterPage.Empty
    }

    override suspend fun getCharacter(id: Int): Character = dataSource.getCharacter(id).toDomain()

    override suspend fun getNeighbours(character: Character): List<Character> {
        val locationId = character.location.id ?: return emptyList()
        val ids = dataSource.getLocation(locationId).residentIds()
            .filter { it != character.id }
            .take(NEIGHBOURS_LIMIT)
        return coroutineScope {
            ids.map { id -> async { dataSource.getCharacter(id).toDomain() } }.awaitAll()
        }
    }
}
