package org.topit.rickmorty.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.key
import androidx.compose.ui.text.intl.Locale

enum class AppLanguage(val code: String) {
    RU("ru"),
    EN("en");

    fun next(): AppLanguage = entries[(ordinal + 1) % entries.size]

    companion object {
        fun system(): AppLanguage =
            entries.firstOrNull { it.code == Locale.current.language } ?: RU
    }
}

expect object LocalAppLocale {
    @Composable
    infix fun provides(value: String?): ProvidedValue<*>
}

@Composable
fun AppLocaleProvider(language: AppLanguage, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppLocale provides language.code) {
        key(language) {
            content()
        }
    }
}
