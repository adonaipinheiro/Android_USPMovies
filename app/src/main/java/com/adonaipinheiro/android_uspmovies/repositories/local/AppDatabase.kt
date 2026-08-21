package com.adonaipinheiro.android_uspmovies.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FavoriteMovieEntity::class, CachedPopularMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun cachedPopularMovieDao(): CachedPopularMovieDao
}
