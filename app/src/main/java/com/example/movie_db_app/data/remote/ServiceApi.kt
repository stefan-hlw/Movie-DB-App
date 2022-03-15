package com.example.movie_db_app.data.remote

import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): Response<MovieListResponse>
}