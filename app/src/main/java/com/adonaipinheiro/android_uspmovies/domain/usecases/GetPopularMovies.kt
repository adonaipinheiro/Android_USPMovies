package com.adonaipinheiro.android_uspmovies.domain.usecases

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import javax.inject.Inject

// camada: domain — não conhece framework
class GetPopularMovies @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> = repository.getPopular(page)
}
