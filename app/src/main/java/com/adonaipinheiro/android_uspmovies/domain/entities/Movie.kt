package com.adonaipinheiro.android_uspmovies.domain.entities

// camada: domain — não conhece framework, rede nem banco.
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseYear: String?,
    val genres: List<String>
) {
    val posterUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
}
