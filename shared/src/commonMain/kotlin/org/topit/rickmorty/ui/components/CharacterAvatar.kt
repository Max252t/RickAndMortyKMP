package org.topit.rickmorty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.topit.rickmorty.ui.model.StatusUi

@Composable
fun CharacterAvatar(
    avatar: DrawableResource?,
    initials: String,
    status: StatusUi,
    size: Dp,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .size(size)
            .border(width = 3.dp, color = status.color, shape = CircleShape),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        if (avatar != null) {
            Image(
                painter = painterResource(avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text(text = initials, style = textStyle)
            }
        }
    }
}
