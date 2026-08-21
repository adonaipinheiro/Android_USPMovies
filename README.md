# USPMovies — Android (Kotlin/Jetpack Compose)

Catálogo de filmes (TMDB) construído como material didático do curso **Arquitetura Mobile I-II** (MBA USP Esalq). Mesma arquitetura das versões iOS (Swift) e React Native — muda só o "sotaque" da linguagem.

## Configurar a API key da TMDB

1. Crie uma conta gratuita em https://developer.themoviedb.org e gere um **API Read Access Token** (token v4, não a `api_key` v3).
2. Copie o arquivo de exemplo:
   ```bash
   cp app/secrets.properties.example app/secrets.properties
   ```
3. Abra `app/secrets.properties` e preencha `TMDB_ACCESS_TOKEN` com o seu token.
4. `secrets.properties` já está no `.gitignore` — nunca será commitado. O valor é exposto ao código via `BuildConfig.TMDB_ACCESS_TOKEN` (gerado a partir desse arquivo em tempo de build).

## Rodar

Abra o projeto no Android Studio (ou rode `./gradlew :app:assembleDebug`) e instale num emulador/dispositivo com Android 15+ (compileSdk 37). Sem o passo acima, `BuildConfig.TMDB_ACCESS_TOKEN` fica vazio e as chamadas à TMDB retornam 401 — configure a chave antes de rodar.

## Arquitetura — 4 camadas

Regra de dependência: tudo aponta para o **Domain**.

```
Presentation → Domain ← Repositories → Infra
```

- **Domain** (`domain/`): entidade `Movie`, casos de uso (`GetPopularMovies`, `SearchMovies`, `GetMovieDetails`, `ToggleFavorite`, `GetFavorites`, `ObserveIsFavorite`) e as interfaces `MoviesRepository`/`FavoritesRepository`. Kotlin puro — sem import de Android/Retrofit/Room.
- **Repositories** (`repositories/`): implementa as interfaces do domínio. Conhece o vocabulário do domínio (fala de `Movie`) e usa o `Infra` por baixo — DTOs da TMDB (Gson), mapeamento DTO↔entidade, entidades/DAOs Room (`FavoriteMovieEntity`, `CachedPopularMovieEntity`) e a lógica de cache/favoritos.
- **Infra** (`infra/`): puramente técnico, não sabe o que é um "filme" — cliente HTTP (Retrofit/OkHttp, `TmdbApi`) e módulos Hilt de rede/banco.
- **Presentation** (`presentation/`): telas Compose "burras" (`PopularScreen`, `SearchScreen`, `DetailScreen`, `FavoritesScreen`) + `ViewModel`s (`@HiltViewModel`) com `StateFlow`, que concentram a lógica de tela. Navegação desacoplada via `AppCoordinator` (`navigation/`).
- **DI**: Hilt monta o grafo — `RepositoryModule` liga `domain` a `repositories` (`@Binds`), `NetworkModule`/`DatabaseModule` ficam em `infra`.

## Stack

Jetpack Compose (Material 3) · ViewModel + StateFlow · Hilt · Retrofit + Gson · Room · Coroutines/Flow · Navigation Compose (rotas tipadas com `kotlinx.serialization`) · Coil 3.

## Funcionalidades

F1 populares (paginado) · F2 busca com debounce · F3 detalhe · F4 favoritar (offline) · F5 tela de favoritos · F6 cache offline de populares (Room).

## Notas de compatibilidade (ao configurar do zero)

- **AGP 9 "built-in Kotlin"**: o KSP (Room/Hilt) ainda registra source sets pela API antiga do Kotlin Gradle Plugin, incompatível por padrão com o Kotlin embutido do AGP 9. Isso já está resolvido via `android.disallowKotlinSourceSets=false` em `gradle.properties`.
- **Coil 3.5.0** traz `kotlin-stdlib:2.4.0` como dependência, mais novo que o compilador Kotlin do projeto (2.2.10) — isso já está resolvido forçando a versão do `kotlin-stdlib` em `app/build.gradle.kts`.
- Optou-se por **Gson** em vez de `kotlinx.serialization` para o conversor do Retrofit, para evitar fricção de visibilidade Kotlin (`internal`) na integração `converter-kotlinx-serialization` nessa combinação de versões.

## Próximos passos

- Testes unitários dos casos de uso com repositórios mockados (JUnit + MockK) — ainda não incluídos nesta versão inicial.
- `COMPARACAO.md` na raiz do curso, depois que as 3 stacks tiverem uma versão inicial.
