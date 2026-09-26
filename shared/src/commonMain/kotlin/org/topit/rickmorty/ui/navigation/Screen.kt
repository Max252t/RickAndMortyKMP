package org.topit.rickmorty.ui.navigation

sealed interface Screen {
    data object CharacterList : Screen
    data class CharacterDetail(val id: Int) : Screen
}
