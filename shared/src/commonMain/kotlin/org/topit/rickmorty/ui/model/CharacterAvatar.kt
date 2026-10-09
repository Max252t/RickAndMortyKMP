package org.topit.rickmorty.ui.model

import org.jetbrains.compose.resources.DrawableResource
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.character_1
import org.topit.rickmorty.resources.character_10
import org.topit.rickmorty.resources.character_11
import org.topit.rickmorty.resources.character_12
import org.topit.rickmorty.resources.character_13
import org.topit.rickmorty.resources.character_14
import org.topit.rickmorty.resources.character_15
import org.topit.rickmorty.resources.character_16
import org.topit.rickmorty.resources.character_17
import org.topit.rickmorty.resources.character_18
import org.topit.rickmorty.resources.character_19
import org.topit.rickmorty.resources.character_2
import org.topit.rickmorty.resources.character_20
import org.topit.rickmorty.resources.character_21
import org.topit.rickmorty.resources.character_22
import org.topit.rickmorty.resources.character_23
import org.topit.rickmorty.resources.character_24
import org.topit.rickmorty.resources.character_3
import org.topit.rickmorty.resources.character_4
import org.topit.rickmorty.resources.character_5
import org.topit.rickmorty.resources.character_6
import org.topit.rickmorty.resources.character_7
import org.topit.rickmorty.resources.character_8
import org.topit.rickmorty.resources.character_9

fun avatarOf(characterId: Int): DrawableResource? = when (characterId) {
    1 -> Res.drawable.character_1
    2 -> Res.drawable.character_2
    3 -> Res.drawable.character_3
    4 -> Res.drawable.character_4
    5 -> Res.drawable.character_5
    6 -> Res.drawable.character_6
    7 -> Res.drawable.character_7
    8 -> Res.drawable.character_8
    9 -> Res.drawable.character_9
    10 -> Res.drawable.character_10
    11 -> Res.drawable.character_11
    12 -> Res.drawable.character_12
    13 -> Res.drawable.character_13
    14 -> Res.drawable.character_14
    15 -> Res.drawable.character_15
    16 -> Res.drawable.character_16
    17 -> Res.drawable.character_17
    18 -> Res.drawable.character_18
    19 -> Res.drawable.character_19
    20 -> Res.drawable.character_20
    21 -> Res.drawable.character_21
    22 -> Res.drawable.character_22
    23 -> Res.drawable.character_23
    24 -> Res.drawable.character_24
    else -> null
}
