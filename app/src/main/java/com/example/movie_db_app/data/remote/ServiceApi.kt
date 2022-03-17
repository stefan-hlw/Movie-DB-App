package com.example.movie_db_app.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): Response<MovieListResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresResponse>
}