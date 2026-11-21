package org.topit.rickmorty.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.action_retry
import org.topit.rickmorty.ui.model.LoadErrorUi

@Composable
fun LoadErrorView(
    error: LoadErrorUi,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val message = error.statusCode
        ?.let { stringResource(error.message, it) }
        ?: stringResource(error.message)
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        FilledTonalButton(onClick = onRetry) {
            Text(stringResource(Res.string.action_retry))
        }
    }
}
