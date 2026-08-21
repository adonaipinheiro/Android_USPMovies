package com.adonaipinheiro.android_uspmovies.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedPopularMovieDao {
    @Query("SELECT * FROM cached_popular_movies ORDER BY position ASC LIMIT 40")
    suspend fun getAll(): List<CachedPopularMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<CachedPopularMovieEntity>)

    @Query("DELETE FROM cached_popular_movies")
    suspend fun clear()
}
