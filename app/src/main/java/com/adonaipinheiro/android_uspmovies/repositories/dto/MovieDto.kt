package com.adonaipinheiro.android_uspmovies.repositories.dto

import com.google.gson.annotations.SerializedName

// camada: repositories — o domínio nunca vê o JSON cru da TMDB.
data class MoviesPageDto(
    val page: Int,
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String? = null,
    val overview: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("release_date") val releaseDate: String? = null,
    val genres: List<GenreDto>? = null
)

data class GenreDto(
    val id: Int,
    val name: String
)
