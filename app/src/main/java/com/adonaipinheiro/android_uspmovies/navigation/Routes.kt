package com.adonaipinheiro.android_uspmovies.navigation

import kotlinx.serialization.Serializable

// camada: presentation — rotas tipadas (Navigation Compose).
@Serializable
data object PopularRoute

@Serializable
data object SearchRoute

@Serializable
data object FavoritesRoute

@Serializable
data class DetailRoute(val movieId: Int)
