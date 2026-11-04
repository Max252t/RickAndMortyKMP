package org.topit.rickmorty.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.topit.rickmorty.data.dto.CharacterDto
import org.topit.rickmorty.data.dto.CharacterPageDto
import org.topit.rickmorty.data.dto.LocationDto

class RickMortyApi(private val client: HttpClient) {

    suspend fun getCharacters(page: Int, name: String): CharacterPageDto =
        client.get("character") {
            parameter("page", page)
            if (name.isNotEmpty()) parameter("name", name)
        }.body()

    suspend fun getCharacter(id: Int): CharacterDto = client.get("character/$id").body()

    suspend fun getLocation(id: Int): LocationDto = client.get("location/$id").body()
}
