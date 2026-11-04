package org.topit.rickmorty.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.detail_episodes
import org.topit.rickmorty.resources.detail_gender
import org.topit.rickmorty.resources.detail_location
import org.topit.rickmorty.resources.detail_not_found
import org.topit.rickmorty.resources.detail_origin
import org.topit.rickmorty.resources.detail_species
import org.topit.rickmorty.resources.detail_status
import org.topit.rickmorty.resources.detail_type
import org.topit.rickmorty.resources.section_info
import org.topit.rickmorty.resources.section_neighbours
import org.topit.rickmorty.ui.components.CharacterAvatar
import org.topit.rickmorty.ui.components.CharacterCard
import org.topit.rickmorty.ui.components.InfoRow
import org.topit.rickmorty.ui.components.LoadErrorView
import org.topit.rickmorty.ui.components.Section
import org.topit.rickmorty.ui.components.StatusLabel
import org.topit.rickmorty.ui.detail.CharacterDetailIntent
import org.topit.rickmorty.ui.detail.CharacterDetailState
import org.topit.rickmorty.ui.model.CharacterDetailUi

@Composable
fun CharacterDetailScreen(
    state: CharacterDetailState,
    onIntent: (CharacterDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        CharacterDetailState.Loading -> Box(modifier, Alignment.Center) {
            CircularProgressIndicator()
        }
        CharacterDetailState.NotFound -> Box(modifier, Alignment.Center) {
            Text(
                text = stringResource(Res.string.detail_not_found),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        is CharacterDetailState.Failure -> Box(modifier, Alignment.Center) {
            LoadErrorView(
                error = state.error,
                onRetry = { onIntent(CharacterDetailIntent.Retried) },
            )
        }
        is CharacterDetailState.Loaded -> CharacterDetailContent(
            character = state.character,
            onIntent = onIntent,
            modifier = modifier,
        )
    }
}

@Composable
private fun CharacterDetailContent(
    character: CharacterDetailUi,
    onIntent: (CharacterDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CharacterAvatar(
            imageUrl = character.imageUrl,
            initials = character.initials,
            status = character.status,
            size = 160.dp,
            textStyle = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        StatusLabel(status = character.status, suffix = character.species)

        Section(stringResource(Res.string.section_info))
        InfoRow(stringResource(Res.string.detail_status), stringResource(character.status.label))
        InfoRow(stringResource(Res.string.detail_species), character.species)
        character.type?.let { InfoRow(stringResource(Res.string.detail_type), it) }
        InfoRow(stringResource(Res.string.detail_gender), stringResource(character.gender))
        InfoRow(stringResource(Res.string.detail_origin), character.origin)
        InfoRow(stringResource(Res.string.detail_location), character.location)
        InfoRow(stringResource(Res.string.detail_episodes), character.episodeCount)

        if (character.neighbours.isNotEmpty()) {
            Section(stringResource(Res.string.section_neighbours))
            character.neighbours.forEach { neighbour ->
                CharacterCard(
                    character = neighbour,
                    onClick = { onIntent(CharacterDetailIntent.NeighbourClicked(neighbour.id)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
