# USPMovies — Android 🎬

![Platform](https://img.shields.io/badge/platform-Android-3ddc84)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2-7f52ff)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-4285f4)
![minSdk](https://img.shields.io/badge/minSdk-24-green)
![compileSdk](https://img.shields.io/badge/compileSdk-37-green)
![Architecture](https://img.shields.io/badge/arquitetura-Clean_+_MVVM_(4_camadas)-8a2be2)

Catálogo de filmes consumindo a API do **TMDB**, em Kotlin/Jetpack Compose. É a
stack Android do app de referência do curso **Arquitetura Mobile I‑II** (MBA em
Engenharia de Software — USP/Esalq). O mesmo escopo funcional e a **mesma
arquitetura** são implementados em paralelo em três stacks — muda só o "sotaque"
da linguagem.

> Projeto didático. O foco é a organização em camadas — não uma publicação real
> na loja.

## Projetos irmãos

| Stack | Repositório | Status |
|---|---|---|
| React Native | [`adonaipinheiro/RN_USPMovies`](https://github.com/adonaipinheiro/RN_USPMovies) | testes 100% · CI/CD (Android) |
| **Android nativo** | `adonaipinheiro/Android_USPMovies` | versão inicial funcional |
| iOS nativo | [`adonaipinheiro/iOS_USPMovies`](https://github.com/adonaipinheiro/iOS_USPMovies) | versão inicial funcional |

## Funcionalidades

| # | Feature | Detalhe |
|---|---|---|
| F1 | Lista de populares | paginação |
| F2 | Busca | com debounce |
| F3 | Detalhe do filme | — |
| F4 | Favoritar / desfavoritar | persistido em Room, funciona offline |
| F5 | Tela de favoritos | lê o snapshot local |
| F6 | Cache offline dos populares | Room, dentro do repositório |

Toda tela de dados trata os estados **loading / data / empty / error**.

## Configurar a API key da TMDB

1. Crie uma conta em https://developer.themoviedb.org e gere um **API Read Access
   Token** (token v4, não a `api_key` v3).
2. Copie o arquivo de exemplo:
   ```bash
   cp app/secrets.properties.example app/secrets.properties
   ```
3. Preencha `TMDB_ACCESS_TOKEN` em `app/secrets.properties`. O arquivo está no
   `.gitignore`; o valor é exposto ao código via `BuildConfig.TMDB_ACCESS_TOKEN`
   (gerado em tempo de build).

Sem esse passo, `BuildConfig.TMDB_ACCESS_TOKEN` fica vazio e as chamadas à TMDB
retornam 401.

## Rodar

Abra no Android Studio ou use a linha de comando. Emulador/dispositivo com API 24+.

```bash
./gradlew :app:assembleDebug        # build
./gradlew :app:installDebug         # instala no device conectado
```

## Arquitetura — 4 camadas

Regra de dependência: tudo aponta para o **Domain**.

```
presentation ──► domain ◄── repositories ──► infra
```

| Camada | Papel | Conteúdo |
|---|---|---|
| `domain/` | regras e contratos, Kotlin puro (sem Android/Retrofit/Room) | entidade `Movie`; interfaces `MoviesRepository` / `FavoritesRepository`; casos de uso `GetPopularMovies`, `SearchMovies`, `GetMovieDetails`, `ToggleFavorite`, `GetFavorites`, `ObserveIsFavorite` |
| `repositories/` | implementam os contratos do domínio; falam de `Movie` | DTOs da TMDB (Gson), mapeamento DTO↔entidade, entidades/DAOs Room (`FavoriteMovieEntity`, `CachedPopularMovieEntity`), lógica de cache e favoritos, `RepositoryModule` (`@Binds`) |
| `infra/` | encanamento técnico, não sabe o que é um "filme" | `TmdbApi` (Retrofit/OkHttp), módulos Hilt de rede e banco |
| `presentation/` | telas Compose "burras" + `ViewModel`s | `PopularScreen`, `SearchScreen`, `DetailScreen`, `FavoritesScreen`; `@HiltViewModel` + `StateFlow`; navegação desacoplada via `AppCoordinator` (`navigation/`) |

**DI:** Hilt monta o grafo — `RepositoryModule` liga `domain` a `repositories`,
`NetworkModule` / `DatabaseModule` ficam em `infra`.

## Stack

Jetpack Compose (Material 3) · ViewModel + StateFlow · Hilt (KSP) · Retrofit +
Gson · Room · Coroutines/Flow · Navigation Compose com rotas tipadas
(`kotlinx.serialization`) · Coil 3.

## Notas de compatibilidade (ao configurar do zero)

- **AGP 9 "built-in Kotlin"**: o KSP (Room/Hilt) registra source sets pela API
  antiga do Kotlin Gradle Plugin, incompatível por padrão com o Kotlin embutido
  do AGP 9. Resolvido com `android.disallowKotlinSourceSets=false` em
  `gradle.properties`.
- **Coil 3.5.0** puxa `kotlin-stdlib:2.4.0`, mais novo que o compilador Kotlin do
  projeto (2.2.10). Resolvido forçando a versão do `kotlin-stdlib` em
  `app/build.gradle.kts`.
- Optou-se por **Gson** em vez de `kotlinx.serialization` no conversor do Retrofit
  para evitar fricção de visibilidade Kotlin (`internal`) na integração
  `converter-kotlinx-serialization` nessa combinação de versões.

## Próximos passos

- Testes unitários dos casos de uso com repositórios mockados (JUnit + MockK) —
  ainda não incluídos (a stack RN já tem cobertura 100%, serve de referência).
- CI/CD no padrão do repo RN, se fizer sentido para a aula.
- `COMPARACAO.md` na raiz do curso, comparando as 3 stacks lado a lado.
