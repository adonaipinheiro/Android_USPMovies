package com.adonaipinheiro.android_uspmovies.repositories

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import com.adonaipinheiro.android_uspmovies.repositories.local.FavoriteMovieDao
import com.adonaipinheiro.android_uspmovies.repositories.local.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// camada: repositories — implementa o protocolo do domínio usando o Infra.
class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoritesRepository {

    override suspend fun getAll(): List<Movie> = dao.getAll().map { it.toDomain() }

    override suspend fun toggle(movie: Movie) {
        val existing = dao.findById(movie.id)
        if (existing != null) {
            dao.delete(existing)
        } else {
            dao.insert(FavoriteMovieEntity.fromDomain(movie))
        }
    }

    override fun observeIsFavorite(id: Int): Flow<Boolean> = dao.observeIsFavorite(id)
}
