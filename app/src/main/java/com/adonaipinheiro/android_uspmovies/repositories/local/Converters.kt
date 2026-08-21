package com.adonaipinheiro.android_uspmovies.repositories.local

import androidx.room.TypeConverter

// camada: repositories — conversão de tipo específica do Room.
class Converters {
    @TypeConverter
    fun fromGenres(genres: List<String>): String = genres.joinToString("|")

    @TypeConverter
    fun toGenres(raw: String): List<String> = if (raw.isEmpty()) emptyList() else raw.split("|")
}
