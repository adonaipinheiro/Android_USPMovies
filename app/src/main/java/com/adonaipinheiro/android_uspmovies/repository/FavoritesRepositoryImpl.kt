package com.adonaipinheiro.android_uspmovies.repository

import com.adonaipinheiro.android_uspmovies.data.local.FavoriteMovieDao
import com.adonaipinheiro.android_uspmovies.data.local.FavoriteMovieEntity
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// camada: repository — implementa o contrato do domain usando a fonte
// local (data/local). Sem fallback aqui: favoritos só existem localmente.
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
