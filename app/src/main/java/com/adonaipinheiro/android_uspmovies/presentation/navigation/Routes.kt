package com.adonaipinheiro.android_uspmovies.presentation.navigation

import kotlinx.serialization.Serializable

// camada: presentation (navegação) — rotas tipadas (Navigation Compose).
@Serializable
data object PopularRoute

@Serializable
data object SearchRoute

@Serializable
data object FavoritesRoute

@Serializable
data class DetailRoute(val movieId: Int)
