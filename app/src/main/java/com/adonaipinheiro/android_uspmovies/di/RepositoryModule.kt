package com.adonaipinheiro.android_uspmovies.di

import com.adonaipinheiro.android_uspmovies.domain.repositories.FavoritesRepository
import com.adonaipinheiro.android_uspmovies.domain.repositories.MoviesRepository
import com.adonaipinheiro.android_uspmovies.repository.FavoritesRepositoryImpl
import com.adonaipinheiro.android_uspmovies.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// camada: DI — liga as interfaces do domain às implementações da camada
// repository. Junto com NetworkModule e DatabaseModule, é o único lugar
// do app que enxerga domain, repository, data e infra ao mesmo tempo.
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
