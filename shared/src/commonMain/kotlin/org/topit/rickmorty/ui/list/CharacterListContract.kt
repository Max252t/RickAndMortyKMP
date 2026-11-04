package org.topit.rickmorty.ui.list

import androidx.compose.runtime.Immutable
import org.topit.rickmorty.ui.model.CharacterCardUi
import org.topit.rickmorty.ui.model.LoadErrorUi

@Immutable
data class CharacterListState(
    val query: String = "",
    val content: CharacterListContent = CharacterListContent.Loading,
)

@Immutable
sealed interface CharacterListContent {
    data object Loading : CharacterListContent
    data object NotFound : CharacterListContent
    data class Failure(val error: LoadErrorUi) : CharacterListContent
    data class Loaded(
        val items: List<CharacterCardUi>,
        val nextPage: Int?,
        val isLoadingMore: Boolean = false,
        val loadMoreError: LoadErrorUi? = null,
    ) : CharacterListContent
}

sealed interface CharacterListIntent {
    data class CardClicked(val id: Int) : CharacterListIntent
    data class QueryChanged(val value: String) : CharacterListIntent
    data object Retried : CharacterListIntent
    data object NextPageRequested : CharacterListIntent
}
