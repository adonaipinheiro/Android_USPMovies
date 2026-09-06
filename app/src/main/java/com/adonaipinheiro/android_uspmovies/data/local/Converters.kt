package com.adonaipinheiro.android_uspmovies.data.local

import androidx.room.TypeConverter

// camada: data — conversão de tipo específica do Room (List<String> ↔ TEXT).
class Converters {
    @TypeConverter
    fun fromGenres(genres: List<String>): String = genres.joinToString("|")

    @TypeConverter
    fun toGenres(raw: String): List<String> = if (raw.isEmpty()) emptyList() else raw.split("|")
}
