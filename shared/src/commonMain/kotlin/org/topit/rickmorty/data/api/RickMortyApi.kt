package org.topit.rickmorty.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.topit.rickmorty.data.dto.CharacterDto
import org.topit.rickmorty.data.dto.CharacterPageDto
import org.topit.rickmorty.data.dto.LocationDto

class RickMortyApi(
    private val client: HttpClient,
    private val parsingDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend fun getCharacters(page: Int, name: String): CharacterPageDto =
        client.get("character") {
            parameter("page", page)
            if (name.isNotEmpty()) parameter("name", name)
        }.parse()

    suspend fun getCharacter(id: Int): CharacterDto = client.get("character/$id").parse()

    suspend fun getLocation(id: Int): LocationDto = client.get("location/$id").parse()

    private suspend inline fun <reified T> HttpResponse.parse(): T =
        withContext(parsingDispatcher) { body<T>() }
}
