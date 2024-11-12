package com.example.monprofil

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    // Films
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): TMDBListeDesFilms

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String
    ): TMDBListeDesFilms

    // Séries
    @GET("trending/tv/week")
    suspend fun lastSeries(@Query("api_key") api_key: String): TMDBListeDesSeries

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String
    ): TMDBListeDesSeries

    // Personnalités
    @GET("trending/person/week")
    suspend fun lastActeurs(@Query("api_key") api_key: String): TMDBListeDesActeurs

    @GET("search/person")
    suspend fun searchActeurs(
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String
    ): TMDBListeDesActeurs
}
