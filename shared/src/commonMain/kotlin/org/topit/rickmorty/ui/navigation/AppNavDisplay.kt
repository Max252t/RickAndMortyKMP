package org.topit.rickmorty.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.topit.rickmorty.ui.detail.CharacterDetailViewModel
import org.topit.rickmorty.ui.detail.CharacterDetailViewModelFactory
import org.topit.rickmorty.ui.list.CharacterListViewModel
import org.topit.rickmorty.ui.list.CharacterListViewModelFactory
import org.topit.rickmorty.ui.screens.detail.CharacterDetailScreen
import org.topit.rickmorty.ui.screens.list.CharacterListScreen

private const val TRANSITION_MS = 300

@Composable
fun AppNavDisplay(
    navigator: Navigator,
    listViewModelFactory: CharacterListViewModelFactory,
    detailViewModelFactory: CharacterDetailViewModelFactory,
    modifier: Modifier = Modifier,
) {
    val backStack by navigator.backStack.collectAsStateWithLifecycle()
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = navigator::back,
        transitionSpec = { slide(SlideDirection.Start) },
        popTransitionSpec = { slide(SlideDirection.End) },
        predictivePopTransitionSpec = { slide(SlideDirection.End) },
        entryProvider = entryProvider {
            entry<Screen.CharacterList> {
                val viewModel: CharacterListViewModel = viewModel(factory = listViewModelFactory)
                val state by viewModel.state.collectAsStateWithLifecycle()
                CharacterListScreen(state = state, onIntent = viewModel::onIntent)
            }
            entry<Screen.CharacterDetail> { key ->
                val viewModel: CharacterDetailViewModel = viewModel(
                    key = "detail-${key.id}",
                    factory = detailViewModelFactory,
                    extras = CharacterDetailViewModelFactory.extrasFor(key.id),
                )
                val state by viewModel.state.collectAsStateWithLifecycle()
                state?.let { CharacterDetailScreen(character = it, onIntent = viewModel::onIntent) }
            }
        },
    )
}

private fun AnimatedContentTransitionScope<*>.slide(direction: SlideDirection): ContentTransform =
    (slideIntoContainer(direction, tween(TRANSITION_MS)) + fadeIn(tween(TRANSITION_MS)))
        .togetherWith(slideOutOfContainer(direction, tween(TRANSITION_MS)) + fadeOut(tween(TRANSITION_MS)))
