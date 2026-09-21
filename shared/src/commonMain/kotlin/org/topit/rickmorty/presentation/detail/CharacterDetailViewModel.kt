package org.topit.rickmorty.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.domain.repositories.getNeighbours
import org.topit.rickmorty.navigation.Navigator
import org.topit.rickmorty.navigation.Screen
import org.topit.rickmorty.ui.model.CharacterDetailUi
import org.topit.rickmorty.ui.model.toDetailUi
import kotlin.reflect.KClass

sealed interface CharacterDetailIntent {
    data class NeighbourClicked(val id: Int) : CharacterDetailIntent
}

class CharacterDetailViewModel(
    private val characterId: Int,
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailUi?>(null)
    val state: StateFlow<CharacterDetailUi?> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val character = repository.getCharacter(characterId)
            _state.value = character.toDetailUi(repository.getNeighbours(character))
        }
    }

    fun onIntent(intent: CharacterDetailIntent) {
        when (intent) {
            is CharacterDetailIntent.NeighbourClicked -> navigator.open(Screen.CharacterDetail(intent.id))
        }
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
