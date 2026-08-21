package com.adonaipinheiro.android_uspmovies.domain.usecases

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import javax.inject.Inject

// camada: domain — não conhece framework
class GetFavorites @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(): List<Movie> = repository.getAll()
}
