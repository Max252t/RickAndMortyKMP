package org.topit.rickmorty.data.datasource

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.ContentConvertException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import org.topit.rickmorty.data.api.RickMortyApi
import org.topit.rickmorty.data.dto.CharacterDto
import org.topit.rickmorty.data.dto.CharacterPageDto
import org.topit.rickmorty.data.dto.LocationDto
import org.topit.rickmorty.domain.errors.LoadException

class NetworkCharacterDataSource(
    private val api: RickMortyApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CharacterDataSource {

    override suspend fun getCharacters(page: Int, name: String): CharacterPageDto =
        guard { api.getCharacters(page, name) }

    override suspend fun getCharacter(id: Int): CharacterDto = guard { api.getCharacter(id) }

    override suspend fun getLocation(id: Int): LocationDto = guard { api.getLocation(id) }

    private suspend fun <T> guard(request: suspend () -> T): T = try {
        withContext(dispatcher) { request() }
    } catch (_: HttpRequestTimeoutException) {
        throw LoadException.Timeout()
    } catch (error: CancellationException) {
        throw error
    } catch (error: ResponseException) {
        throw when (error.response.status) {
            HttpStatusCode.NotFound -> LoadException.NotFound()
            else -> LoadException.Server(error.response.status.value)
        }
    } catch (_: ContentConvertException) {
        throw LoadException.BadResponse()
    } catch (_: NoTransformationFoundException) {
        throw LoadException.BadResponse()
    } catch (_: SerializationException) {
        throw LoadException.BadResponse()
    } catch (_: Throwable) {
        throw LoadException.NoConnection()
    }
}
