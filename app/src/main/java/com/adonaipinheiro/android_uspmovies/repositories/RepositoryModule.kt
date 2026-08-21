package com.adonaipinheiro.android_uspmovies.repositories

import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// camada: DI — liga as interfaces do domínio às implementações da camada repositories.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}
