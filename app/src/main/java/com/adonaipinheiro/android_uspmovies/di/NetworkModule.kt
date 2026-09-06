package com.adonaipinheiro.android_uspmovies.di

import com.adonaipinheiro.android_uspmovies.BuildConfig
import com.adonaipinheiro.android_uspmovies.data.remote.TmdbApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// camada: DI — monta o encanamento de rede (Gson/OkHttp/Retrofit, que
// seriam infra genérica) e já entrega o TmdbApi pronto (que é data,
// pois conhece MovieDto). Por misturar as duas coisas, este módulo é DI,
// não infra: é o único lugar que liga o genérico ao específico do app.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
            .addHeader("Accept", "application/json")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(authInterceptor)
        if (BuildConfig.DEBUG) {
            // "monitors" de request/response em __DEV__, análogo aos interceptors do ImpactaRN.
            builder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)
}
