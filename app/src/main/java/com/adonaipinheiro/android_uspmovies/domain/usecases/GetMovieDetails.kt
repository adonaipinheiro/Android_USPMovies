package com.adonaipinheiro.android_uspmovies.domain.usecases

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import javax.inject.Inject

// camada: domain — não conhece framework
class GetMovieDetails @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Movie = repository.getDetails(id)
}
