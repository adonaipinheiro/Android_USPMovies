package com.adonaipinheiro.android_uspmovies.domain.repositories

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie

// camada: domain — não conhece framework, rede nem banco.
interface MoviesRepository {
    suspend fun getPopular(page: Int): List<Movie>
    suspend fun search(query: String, page: Int): List<Movie>
    suspend fun getDetails(id: Int): Movie
}
