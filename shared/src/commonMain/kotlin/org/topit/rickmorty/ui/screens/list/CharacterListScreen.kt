package org.topit.rickmorty.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.list_empty
import org.topit.rickmorty.resources.search_hint
import org.topit.rickmorty.ui.components.CharacterCard
import org.topit.rickmorty.ui.components.LoadErrorView
import org.topit.rickmorty.ui.list.CharacterListContent
import org.topit.rickmorty.ui.list.CharacterListIntent
import org.topit.rickmorty.ui.list.CharacterListState

private const val LOAD_MORE_THRESHOLD = 3

@Composable
fun CharacterListScreen(
    state: CharacterListState,
    onIntent: (CharacterListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { onIntent(CharacterListIntent.QueryChanged(it)) },
            label = { Text(stringResource(Res.string.search_hint)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        )
        val contentModifier = Modifier.fillMaxWidth().weight(1f)
        when (val content = state.content) {
            CharacterListContent.Loading -> Box(contentModifier, Alignment.Center) {
                CircularProgressIndicator()
            }
            CharacterListContent.NotFound -> Box(contentModifier, Alignment.Center) {
                Text(
                    text = stringResource(Res.string.list_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            is CharacterListContent.Failure -> Box(contentModifier, Alignment.Center) {
                LoadErrorView(
                    error = content.error,
                    onRetry = { onIntent(CharacterListIntent.Retried) },
                )
            }
            is CharacterListContent.Loaded -> CharacterList(
                content = content,
                onIntent = onIntent,
                modifier = contentModifier,
            )
        }
    }
}

@Composable
private fun CharacterList(
    content: CharacterListContent.Loaded,
    onIntent: (CharacterListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val isNearEnd by remember(listState) {
        derivedStateOf {
            val layout = listState.layoutInfo
            val lastVisible = layout.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= layout.totalItemsCount - LOAD_MORE_THRESHOLD
        }
    }
    val canLoadMore = content.nextPage != null && !content.isLoadingMore && content.loadMoreError == null
    LaunchedEffect(isNearEnd, canLoadMore) {
        if (isNearEnd && canLoadMore) onIntent(CharacterListIntent.NextPageRequested)
    }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(content.items, key = { it.id }) { character ->
            CharacterCard(
                character = character,
                onClick = { onIntent(CharacterListIntent.CardClicked(character.id)) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (content.isLoadingMore) {
            item(key = "loading") {
                Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
        content.loadMoreError?.let { error ->
            item(key = "error") {
                Box(Modifier.fillMaxWidth(), Alignment.Center) {
                    LoadErrorView(
                        error = error,
                        onRetry = { onIntent(CharacterListIntent.NextPageRequested) },
                    )
                }
            }
        }
    }
}
