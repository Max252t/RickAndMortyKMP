package org.topit.rickmorty

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.topit.rickmorty.data.RickMortyRepositoryImpl
import org.topit.rickmorty.data.api.RickMortyApi
import org.topit.rickmorty.data.datasource.NetworkCharacterDataSource
import org.topit.rickmorty.data.network.createHttpClient
import org.topit.rickmorty.domain.repositories.RickMortyRepository
import org.topit.rickmorty.ui.navigation.AppNavDisplay
import org.topit.rickmorty.ui.navigation.Navigator
import org.topit.rickmorty.ui.detail.CharacterDetailViewModelFactory
import org.topit.rickmorty.ui.list.CharacterListViewModelFactory
import org.topit.rickmorty.resources.Res
import org.topit.rickmorty.resources.action_toggle_language
import org.topit.rickmorty.resources.action_toggle_theme
import org.topit.rickmorty.resources.ic_theme
import org.topit.rickmorty.resources.language_code
import org.topit.rickmorty.ui.components.AppScaffold
import org.topit.rickmorty.ui.locale.AppLanguage
import org.topit.rickmorty.ui.locale.AppLocaleProvider
import org.topit.rickmorty.ui.theme.AppTheme

private val repository: RickMortyRepository by lazy {
    RickMortyRepositoryImpl(NetworkCharacterDataSource(RickMortyApi(createHttpClient())))
}

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).diskCache(null).build()
    }

    var darkTheme by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf(AppLanguage.system()) }

    val navigator = remember { Navigator() }
    val listViewModelFactory = remember { CharacterListViewModelFactory(navigator, repository) }
    val detailViewModelFactory = remember { CharacterDetailViewModelFactory(navigator, repository) }
    val backStack by navigator.backStack.collectAsStateWithLifecycle()

    AppLocaleProvider(language) {
        AppTheme(darkTheme) {
            AppScaffold(
                onBack = if (backStack.size > 1) navigator::back else null,
                actions = {
                    val languageDescription = stringResource(Res.string.action_toggle_language)
                    TextButton(
                        onClick = { language = language.next() },
                        modifier = Modifier.semantics { contentDescription = languageDescription },
                    ) {
                        Text(stringResource(Res.string.language_code))
                    }
                    IconButton(onClick = { darkTheme = !darkTheme }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_theme),
                            contentDescription = stringResource(Res.string.action_toggle_theme),
                        )
                    }
                },
            ) { modifier ->
                AppNavDisplay(
                    navigator = navigator,
                    listViewModelFactory = listViewModelFactory,
                    detailViewModelFactory = detailViewModelFactory,
                    modifier = modifier,
                )
            }
        }
    }
}
