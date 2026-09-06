# infra — camada de infraestrutura genérica

Esta pasta existe para deixar explícito o papel da camada `infra`, mesmo
sem conter código de produção no momento.

`infra` seria o lugar de encanamento **100% genérico**: código que não
sabe o que é um "filme" e não pode importar nada de `domain` (nem
`Movie`, nem `MovieDto`). Exemplos do que caberia aqui: um wrapper de
`OkHttpClient` que só configura timeouts/logging sem saber que existe um
`TmdbApi`, ou um builder de `RoomDatabase` que não referencia nenhuma
`@Entity` concreta.

No estado atual do app, os módulos que fariam esse papel
(`NetworkModule`, `DatabaseModule`) já entregam, no mesmo `@Provides`,
peças específicas do domínio de filmes (`TmdbApi`, `AppDatabase` com
suas entidades) — ou seja, eles também compõem o grafo de dependências.
Por isso foram classificados como `di/`, e não como `infra/`. Ver
`di/NetworkModule.kt` e `di/DatabaseModule.kt` para o racional completo.

Se o app crescer e for necessário isolar de fato um pedaço genérico
(por exemplo, um `HttpClientFactory` reutilizável por múltiplos apps,
sem nenhuma menção a `TmdbApi`), ele deve entrar aqui, em `infra/`, e o
`di/` passa a apenas montá-lo — nunca o contrário.
