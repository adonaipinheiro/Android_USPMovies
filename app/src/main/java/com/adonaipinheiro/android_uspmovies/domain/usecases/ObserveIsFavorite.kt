package com.adonaipinheiro.android_uspmovies.domain.usecases

import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// camada: domain — não conhece framework
class ObserveIsFavorite @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = repository.observeIsFavorite(id)
}
