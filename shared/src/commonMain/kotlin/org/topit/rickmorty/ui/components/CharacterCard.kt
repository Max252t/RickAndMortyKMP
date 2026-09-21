package org.topit.rickmorty.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.topit.rickmorty.ui.model.CharacterCardUi

@Composable
fun CharacterCard(
    character: CharacterCardUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CardSurface(onClick = onClick, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharacterAvatar(
                avatar = character.avatar,
                initials = character.initials,
                status = character.status,
                size = 56.dp,
                textStyle = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                StatusLabel(status = character.status, suffix = character.species)
            }
        }
    }
}
