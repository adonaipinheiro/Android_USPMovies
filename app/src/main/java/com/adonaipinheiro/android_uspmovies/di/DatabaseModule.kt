package com.adonaipinheiro.android_uspmovies.di

import android.content.Context
import androidx.room.Room
import com.adonaipinheiro.android_uspmovies.data.local.AppDatabase
import com.adonaipinheiro.android_uspmovies.data.local.CachedPopularMovieDao
import com.adonaipinheiro.android_uspmovies.data.local.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// camada: DI — instancia o Room (Room.databaseBuilder seria infra genérica
// se não apontasse pro AppDatabase::class, que já é o schema de filmes) e
// expõe os DAOs concretos. Mesmo racional do NetworkModule.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "uspmovies.db").build()

    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao = database.favoriteMovieDao()

    @Provides
    fun provideCachedPopularMovieDao(database: AppDatabase): CachedPopularMovieDao =
        database.cachedPopularMovieDao()
}
