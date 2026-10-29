package org.topit.rickmorty.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.ui.navigation.Navigator
import org.topit.rickmorty.ui.navigation.Screen
import org.topit.rickmorty.ui.model.getCards
import kotlin.reflect.KClass

class CharacterListViewModel(
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        search(filter = null)
    }

    fun onIntent(intent: CharacterListIntent) {
        when (intent) {
            is CharacterListIntent.CardClicked -> navigator.open(Screen.CharacterDetail(intent.id))
            is CharacterListIntent.QueryChanged -> {
                _state.update { it.copy(query = intent.value) }
                search(filter = intent.value)
            }
        }
    }

    private fun search(filter: String?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val items = repository.getCards(filter)
            _state.update { it.copy(items = items) }
        }
    }
}

class CharacterListViewModelFactory(
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return CharacterListViewModel(navigator = navigator, repository = repository) as T
    }
}
