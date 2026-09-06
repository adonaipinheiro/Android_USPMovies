package com.adonaipinheiro.android_uspmovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie

// camada: data — esquema de cache offline (F6) + conversão de/para Movie.
// O mapeamento fica junto da entidade (não em data/mapper) porque é
// conversão de persistência local, não de payload de rede.
@Entity(tableName = "cached_popular_movies")
data class CachedPopularMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val voteAverage: Double,
    val releaseYear: String?,
    val genres: List<String>,
    val position: Int
) {
    fun toDomain(): Movie = Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseYear = releaseYear,
        genres = genres
    )

    companion object {
        fun fromDomain(movie: Movie, position: Int) = CachedPopularMovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath,
            overview = movie.overview,
            voteAverage = movie.voteAverage,
            releaseYear = movie.releaseYear,
            genres = movie.genres,
            position = position
        )
    }
}
