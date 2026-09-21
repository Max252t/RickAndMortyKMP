package org.topit.rickmorty.navigation

sealed interface Screen {
    data object CharacterList : Screen
    data class CharacterDetail(val id: Int) : Screen
}
