package org.topit.rickmorty.ui.model

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import org.topit.rickmorty.domain.errors.LoadException
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.error_bad_response
import org.topit.rickmorty.resources.error_no_connection
import org.topit.rickmorty.resources.error_not_found
import org.topit.rickmorty.resources.error_server
import org.topit.rickmorty.resources.error_timeout

@Immutable
data class LoadErrorUi(
    val message: StringResource,
    val statusCode: Int? = null,
)

fun LoadException.toUi(): LoadErrorUi = when (this) {
    is LoadException.NoConnection -> LoadErrorUi(Res.string.error_no_connection)
    is LoadException.Timeout -> LoadErrorUi(Res.string.error_timeout)
    is LoadException.NotFound -> LoadErrorUi(Res.string.error_not_found)
    is LoadException.Server -> LoadErrorUi(Res.string.error_server, statusCode)
    is LoadException.BadResponse -> LoadErrorUi(Res.string.error_bad_response)
}
