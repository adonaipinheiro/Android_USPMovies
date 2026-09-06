package com.adonaipinheiro.android_uspmovies.repository

import com.adonaipinheiro.android_uspmovies.data.local.CachedPopularMovieDao
import com.adonaipinheiro.android_uspmovies.data.local.CachedPopularMovieEntity
import com.adonaipinheiro.android_uspmovies.data.mapper.toDomain
import com.adonaipinheiro.android_uspmovies.data.remote.TmdbApi
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import javax.inject.Inject

// camada: repository — implementa o contrato do domain orquestrando as
// fontes de data (remota via TmdbApi, local via CachedPopularMovieDao).
// A política de negócio (fallback offline) mora aqui, não em data: data só
// sabe buscar/gravar, quem decide "quando usar o cache" é o repository.
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
