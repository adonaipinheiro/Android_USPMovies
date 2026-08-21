package com.adonaipinheiro.android_uspmovies.repositories

import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import com.adonaipinheiro.android_uspmovies.infra.network.TmdbApi
import com.adonaipinheiro.android_uspmovies.repositories.local.CachedPopularMovieDao
import com.adonaipinheiro.android_uspmovies.repositories.local.CachedPopularMovieEntity
import javax.inject.Inject

// camada: repositories — implementa o protocolo do domínio usando o Infra.
class MoviesRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val cachedPopularMovieDao: CachedPopularMovieDao
) : MoviesRepository {

    override suspend fun getPopular(page: Int): List<Movie> {
        return try {
            val movies = api.getPopular(page).results.map { it.toDomain() }
            if (page == 1) cachePopular(movies)
            movies
        } catch (error: Exception) {
            // F6: sem rede na primeira página, cai para o cache local.
            if (page == 1) {
                val cached = cachedPopularMovieDao.getAll().map { it.toDomain() }
                if (cached.isNotEmpty()) return cached
            }
            throw error
        }
    }

    override suspend fun search(query: String, page: Int): List<Movie> =
        api.search(query, page).results.map { it.toDomain() }

    override suspend fun getDetails(id: Int): Movie =
        api.getDetails(id).toDomain()

    private suspend fun cachePopular(movies: List<Movie>) {
        cachedPopularMovieDao.clear()
        cachedPopularMovieDao.insertAll(
            movies.mapIndexed { index, movie -> CachedPopularMovieEntity.fromDomain(movie, index) }
        )
    }
}
