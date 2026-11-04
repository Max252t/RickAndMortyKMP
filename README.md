# Рик и Морти — Compose Multiplatform

Учебное кроссплатформенное приложение по вселенной мультсериала «Рик и Морти».
Каркас на Compose Multiplatform, данные приходят из сети через Ktor.

## Предметная область

Каталог персонажей сериала. Источник данных — [rickandmortyapi.com](https://rickandmortyapi.com),
публичное REST API без авторизации.

Сущности домена:

| Сущность | Что описывает | Ключевые поля |
|---|---|---|
| `Character` | персонаж | имя, статус (жив / мёртв / неизвестно), вид, подвид, пол, место рождения, последнее местоположение, эпизоды |

Экраны:

- **Список персонажей** — данные с сервера постранично, поиск по имени на сервере. У карточки видны аватар, имя, статус и вид.
- **Карточка персонажа** — досье: статус, вид, подвид, пол, происхождение, последнее местоположение,
  число эпизодов. Ниже блок «Сейчас там же»: персонажи с тем же последним местоположением,
  до 20 записей загружаются параллельно, каждая открывается как новый экран.

## Запуск

Нужен JDK 21. Остальное Gradle скачает сам, первый sync долгий.

Desktop — ведущий таргет вехи:

```bash
./gradlew :desktopApp:run
```

Web (wasm), бонусом:

```bash
./gradlew :webApp:wasmJsBrowserDevelopmentRun
```

Android (нужен настроенный Android SDK):

```bash
./gradlew :androidApp:assembleDebug
```

## Проверки

Паритет ключей в локалях:

```bash
python tools/check-strings.py
```

Отчёт компилятора Compose о стабильности и пропуске рекомпозиции
(после сборки лежит в `shared/build/compose_compiler/`):

```bash
./gradlew :shared:compileKotlinJvm
```

## Структура

| Путь | Что внутри |
|---|---|
| `shared/src/commonMain/kotlin/.../domain` | сущности и интерфейс репозитория, без деталей транспорта |
| `shared/src/commonMain/kotlin/.../data` | HTTP-клиент Ktor, API-клиент, датасорс с переводом ошибок в `LoadException`, DTO, мапперы, репозиторий |
| `shared/src/commonMain/kotlin/.../ui/list`, `.../ui/detail` | состояние, намерения и ViewModel экранов |
| `shared/src/commonMain/kotlin/.../ui/screens` | сами экраны, рисуются из состояния |
| `shared/src/commonMain/kotlin/.../ui/navigation` | маршруты, бэкстек и `NavDisplay` |
| `shared/src/commonMain/kotlin/.../ui/model` | UI-модели и мапперы из домена |
| `shared/src/commonMain/kotlin/.../ui/theme` | светлая и тёмная схемы цветов |
| `shared/src/commonMain/kotlin/.../ui/locale` | переключение языка в рантайме, по реализации на платформу |
| `shared/src/commonMain/composeResources` | подписи интерфейса (ru, en) и иконки |
| `tools/check-strings.py` | сверка ключей локалей |

## Технические решения

- **Навигация** — Navigation 3, мультиплатформенная сборка UI-части
  (`org.jetbrains.androidx.navigation3:navigation3-ui`). Маршрут — объект: `sealed interface Screen`.
  Бэкстек — обычный список в `StateFlow` внутри `Navigator`.
- **Состояние** — MVI: вниз в composable уходят состояние и колбэк намерений, ViewModel целиком не передаётся.
- **Тема** — токены `MaterialTheme`, светлая и тёмная. Зашитый цвет один: цвет статуса персонажа,
  он несёт смысл данных и от темы не зависит.
- **Подписи** — `composeResources`, две локали. Язык и тема переключаются в шапке и живут в памяти сессии.
- **Данные каталога не переводятся**: имена, виды и названия локаций остаются на английском, как в API.
- **Сеть** — цепочка `HttpClient` → `RickMortyApi` → `NetworkCharacterDataSource` → `RickMortyRepositoryImpl` → ViewModel. Ktor известен только слою data, ViewModel видит доменную `LoadException`.
- **Адрес API** не лежит в коде: он задан свойством `rickmorty.api.baseUrl` в `gradle.properties`, из него при сборке генерируется `ApiConfig.kt`.
- **Движки HTTP-клиента** выбираются через `expect`/`actual`: OkHttp на Android, CIO на JVM, Js в браузере.
- **Аватары** — Coil 3 с сетевым модулем Ktor. Пока картинка не загрузилась или если загрузка не удалась, видны инициалы.

## Ограничения

- Нет локального хранилища: тема, язык и запрос поиска живут только в памяти сессии.
- Нет адаптива под ширину окна, deep links и адресной строки браузера — это В3.
- iOS-таргет не заведён.
