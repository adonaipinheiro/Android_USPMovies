package com.adonaipinheiro.android_uspmovies.infra.local

import android.content.Context
import androidx.room.Room
import com.adonaipinheiro.android_uspmovies.repositories.local.AppDatabase
import com.adonaipinheiro.android_uspmovies.repositories.local.CachedPopularMovieDao
import com.adonaipinheiro.android_uspmovies.repositories.local.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// camada: infra — plumbing técnica genérica, não conhece o domínio.
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
