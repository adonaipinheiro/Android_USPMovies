package com.adonaipinheiro.android_uspmovies.data.remote.dto

import com.google.gson.annotations.SerializedName

// camada: data — formato bruto da resposta da TMDB (JSON); o domínio nunca
// vê isso diretamente, só a entidade Movie mapeada pelo data/mapper.
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
