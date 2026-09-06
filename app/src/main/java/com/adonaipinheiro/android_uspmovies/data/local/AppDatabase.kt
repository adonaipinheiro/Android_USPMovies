package com.adonaipinheiro.android_uspmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// camada: data — esquema Room concreto do app (conhece as entidades de
// filme). O builder genérico do Room (Room.databaseBuilder) fica no di/,
// que é quem monta a instância; aqui só se declara o schema.
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
