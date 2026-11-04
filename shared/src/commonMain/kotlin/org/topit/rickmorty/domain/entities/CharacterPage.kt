package org.topit.rickmorty.domain.entities

data class CharacterPage(
    val items: List<Character>,
    val hasMore: Boolean,
) {
    companion object {
        val Empty = CharacterPage(items = emptyList(), hasMore = false)
    }
}
