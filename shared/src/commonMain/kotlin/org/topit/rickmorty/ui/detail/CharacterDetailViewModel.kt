package org.topit.rickmorty.ui.detail

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.topit.rickmorty.domain.entities.Character
import org.topit.rickmorty.domain.errors.LoadException
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.ui.model.CharacterDetailUi
import org.topit.rickmorty.ui.model.LoadErrorUi
import org.topit.rickmorty.ui.model.toDetailUi
import org.topit.rickmorty.ui.model.toUi
import org.topit.rickmorty.ui.navigation.Navigator
import org.topit.rickmorty.ui.navigation.Screen
import kotlin.reflect.KClass

@Immutable
sealed interface CharacterDetailState {
    data object Loading : CharacterDetailState
    data object NotFound : CharacterDetailState
    data class Failure(val error: LoadErrorUi) : CharacterDetailState
    data class Loaded(val character: CharacterDetailUi) : CharacterDetailState
}

sealed interface CharacterDetailIntent {
    data class NeighbourClicked(val id: Int) : CharacterDetailIntent
    data object Retried : CharacterDetailIntent
}

class CharacterDetailViewModel(
    private val characterId: Int,
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    init {
        load()
    }

    fun onIntent(intent: CharacterDetailIntent) {
        when (intent) {
            is CharacterDetailIntent.NeighbourClicked -> navigator.open(Screen.CharacterDetail(intent.id))
            CharacterDetailIntent.Retried -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.value = CharacterDetailState.Loading
            _state.value = try {
                val character = repository.getCharacter(characterId)
                CharacterDetailState.Loaded(character.toDetailUi(loadNeighbours(character)))
            } catch (_: LoadException.NotFound) {
                CharacterDetailState.NotFound
            } catch (error: LoadException) {
                CharacterDetailState.Failure(error.toUi())
            }
        }
    }

    private suspend fun loadNeighbours(character: Character): List<Character> = try {
        repository.getNeighbours(character)
    } catch (_: LoadException) {
        emptyList()
    }
}

class CharacterDetailViewModelFactory(
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val characterId = checkNotNull(extras[CharacterIdKey]) { "characterId is missing in CreationExtras" }
        @Suppress("UNCHECKED_CAST")
        return CharacterDetailViewModel(
            characterId = characterId,
            navigator = navigator,
            repository = repository,
        ) as T
    }

    companion object {
        val CharacterIdKey = CreationExtras.Key<Int>()

        fun extrasFor(characterId: Int): CreationExtras =
            MutableCreationExtras().apply { set(CharacterIdKey, characterId) }
    }
}
