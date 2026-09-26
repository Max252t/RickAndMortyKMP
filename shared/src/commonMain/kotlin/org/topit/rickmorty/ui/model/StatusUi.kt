package org.topit.rickmorty.ui.model

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import org.topit.rickmorty.domain.entities.Gender
import org.topit.rickmorty.domain.entities.Status
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.gender_female
import org.topit.rickmorty.resources.gender_genderless
import org.topit.rickmorty.resources.gender_male
import org.topit.rickmorty.resources.gender_unknown
import org.topit.rickmorty.resources.status_alive
import org.topit.rickmorty.resources.status_dead
import org.topit.rickmorty.resources.status_unknown

data class StatusUi(val label: StringResource, val color: Color)

private val AliveColor = Color(0xFF4CAF50)
private val DeadColor = Color(0xFFE53935)
private val UnknownColor = Color(0xFF9E9E9E)

fun Status.toUi(): StatusUi = when (this) {
    Status.ALIVE -> StatusUi(Res.string.status_alive, AliveColor)
    Status.DEAD -> StatusUi(Res.string.status_dead, DeadColor)
    Status.UNKNOWN -> StatusUi(Res.string.status_unknown, UnknownColor)
}

fun Gender.label(): StringResource = when (this) {
    Gender.FEMALE -> Res.string.gender_female
    Gender.MALE -> Res.string.gender_male
    Gender.GENDERLESS -> Res.string.gender_genderless
    Gender.UNKNOWN -> Res.string.gender_unknown
}
