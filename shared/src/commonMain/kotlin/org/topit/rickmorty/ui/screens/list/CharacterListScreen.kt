package org.topit.rickmorty.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.topit.rickmorty.presentation.list.CharacterListIntent
import org.topit.rickmorty.presentation.list.CharacterListState
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.list_empty
import org.topit.rickmorty.resources.search_hint
import org.topit.rickmorty.ui.components.CharacterCard

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
        if (state.items.isEmpty()) {
            Text(
                text = stringResource(Res.string.list_empty),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp),
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.items, key = { it.id }) { character ->
                CharacterCard(
                    character = character,
                    onClick = { onIntent(CharacterListIntent.CardClicked(character.id)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
