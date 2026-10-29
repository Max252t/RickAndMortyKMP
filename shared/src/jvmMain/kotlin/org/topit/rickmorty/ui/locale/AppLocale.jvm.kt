package org.topit.rickmorty.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

actual object LocalAppLocale {
    private var default: Locale? = null
    private val LocalAppLocale = staticCompositionLocalOf { Locale.getDefault().toString() }

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val system = default ?: Locale.getDefault().also { default = it }
        val locale = value?.let(Locale::forLanguageTag) ?: system
        Locale.setDefault(locale)
        return LocalAppLocale.provides(locale.toString())
    }
}
