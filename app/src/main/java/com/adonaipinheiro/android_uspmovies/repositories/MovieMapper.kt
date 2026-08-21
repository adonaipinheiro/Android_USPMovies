package com.adonaipinheiro.android_uspmovies.repositories

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.repositories.dto.MovieDto

// camada: repositories — mapeia DTO(TMDB) ↔ entidade de domínio.
fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
    releaseYear = releaseDate?.takeIf { it.length >= 4 }?.substring(0, 4),
    genres = genres?.map { it.name } ?: emptyList()
)
