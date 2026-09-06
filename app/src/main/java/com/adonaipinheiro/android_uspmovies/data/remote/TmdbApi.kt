package com.adonaipinheiro.android_uspmovies.data.remote

import com.adonaipinheiro.android_uspmovies.data.remote.dto.MovieDto
import com.adonaipinheiro.android_uspmovies.data.remote.dto.MoviesPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// camada: data — fonte remota (TMDB); a assinatura fala DTO, não domínio.
// Antes vivia em infra/network, mas conhece MovieDto/MoviesPageDto — logo é
// específica do app, não plumbing genérica. Infra não pode saber disso.
interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int): MoviesPageDto

    @GET("search/movie")
    suspend fun search(@Query("query") query: String, @Query("page") page: Int): MoviesPageDto

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Int): MovieDto
}
