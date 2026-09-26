package org.topit.rickmorty.ui.list

import androidx.compose.runtime.Immutable
import org.topit.rickmorty.ui.model.CharacterCardUi

@Immutable
data class CharacterListState(
    val query: String = "",
    val items: List<CharacterCardUi> = emptyList(),
)

sealed interface CharacterListIntent {
    data class CardClicked(val id: Int) : CharacterListIntent
    data class QueryChanged(val value: String) : CharacterListIntent
}
