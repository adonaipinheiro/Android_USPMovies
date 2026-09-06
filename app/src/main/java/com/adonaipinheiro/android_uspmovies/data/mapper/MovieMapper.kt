package com.adonaipinheiro.android_uspmovies.data.mapper

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.data.remote.dto.MovieDto

// camada: data — mapeia DTO(TMDB) ↔ entidade de domínio. Fica em data (não
// em repository) porque é conversão de formato de uma fonte específica,
// não decisão de política de negócio (isso é do repository).
fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
    releaseYear = releaseDate?.takeIf { it.length >= 4 }?.substring(0, 4),
    genres = genres?.map { it.name } ?: emptyList()
)
