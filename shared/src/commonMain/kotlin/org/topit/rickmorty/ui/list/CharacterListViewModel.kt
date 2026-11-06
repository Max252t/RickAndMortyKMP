package org.topit.rickmorty.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.topit.rickmorty.domain.errors.LoadException
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.ui.model.toCardsUi
import org.topit.rickmorty.ui.model.toUi
import org.topit.rickmorty.ui.navigation.Navigator
import org.topit.rickmorty.ui.navigation.Screen
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.milliseconds

private val SEARCH_DELAY = 400.milliseconds
private const val FIRST_PAGE = 1

class CharacterListViewModel(
    private val navigator: Navigator,
    private val repository: RickMortyRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    private var loadJob: Job? = null
    private var nextPageJob: Job? = null

    init {
        load()
    }

    fun onIntent(intent: CharacterListIntent) {
        when (intent) {
            is CharacterListIntent.CardClicked -> navigator.open(Screen.CharacterDetail(intent.id))
            is CharacterListIntent.QueryChanged -> onQueryChanged(intent.value)
            CharacterListIntent.Retried -> load()
            CharacterListIntent.NextPageRequested -> loadNextPage()
        }
    }

    private fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        load(delayBeforeRequest = true)
    }

    private fun load(delayBeforeRequest: Boolean = false) {
        loadJob?.cancel()
        nextPageJob?.cancel()
        loadJob = viewModelScope.launch {
            if (delayBeforeRequest) delay(SEARCH_DELAY)
            _state.update { it.copy(content = CharacterListContent.Loading) }
            val content = try {
                val page = repository.getCharacters(FIRST_PAGE, _state.value.query)
                if (page.items.isEmpty()) {
                    CharacterListContent.NotFound
                } else {
                    CharacterListContent.Loaded(
                        items = page.items.toCardsUi(),
                        nextPage = if (page.hasMore) FIRST_PAGE + 1 else null,
                    )
                }
            } catch (error: LoadException) {
                CharacterListContent.Failure(error.toUi())
            }
            _state.update { it.copy(content = content) }
        }
    }

    private fun loadNextPage() {
        if (loadJob?.isActive == true) return
        val current = _state.value.content as? CharacterListContent.Loaded ?: return
        val nextPage = current.nextPage ?: return
        if (current.isLoadingMore) return
        val loading = current.copy(isLoadingMore = true, loadMoreError = null)
        _state.update { it.copy(content = loading) }
        nextPageJob = viewModelScope.launch {
            val content = try {
                val page = repository.getCharacters(nextPage, _state.value.query)
                val items = withContext(dispatcher) { current.items + page.items.toCardsUi() }
                current.copy(
                    items = items,
                    nextPage = if (page.hasMore) nextPage + 1 else null,
                    loadMoreError = null,
                )
            } catch (error: LoadException) {
                current.copy(loadMoreError = error.toUi())
            }
            _state.update { it.copy(content = content) }
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
