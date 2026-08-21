package com.adonaipinheiro.android_uspmovies.domain.repositories

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

// camada: domain — não conhece framework, rede nem banco.
// toggle recebe o Movie inteiro (e não só o id) para permitir persistir uma
// cópia local completa do filme — é isso que faz a tela de favoritos
// funcionar 100% offline, sem depender de uma nova chamada de rede.
interface FavoritesRepository {
    suspend fun getAll(): List<Movie>
    suspend fun toggle(movie: Movie)
    fun observeIsFavorite(id: Int): Flow<Boolean>
}
