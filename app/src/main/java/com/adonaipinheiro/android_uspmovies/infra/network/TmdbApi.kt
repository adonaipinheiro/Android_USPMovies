package com.adonaipinheiro.android_uspmovies.infra.network

import com.adonaipinheiro.android_uspmovies.repositories.dto.MovieDto
import com.adonaipinheiro.android_uspmovies.repositories.dto.MoviesPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// camada: infra — plumbing técnica genérica; a assinatura fala DTO, não domínio.
interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int): MoviesPageDto

    @GET("search/movie")
    suspend fun search(@Query("query") query: String, @Query("page") page: Int): MoviesPageDto

    @GET("movie/{id}")
    suspend fun getDetails(@Path("id") id: Int): MovieDto
}
