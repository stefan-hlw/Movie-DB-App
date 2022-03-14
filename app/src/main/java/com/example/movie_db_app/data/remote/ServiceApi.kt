package com.example.movie_db_app.data.remote

import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("trending/movie/day?api_key=a3617389dcc3174c6180ba471833e799")
    suspend fun getTrendingMovies(): Response<MovieListResponse>
}