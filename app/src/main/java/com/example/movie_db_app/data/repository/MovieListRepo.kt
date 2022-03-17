package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.remote.MovieListResponse
import retrofit2.Response

interface MovieListRepo {
    suspend fun getTrendingMovies(): Response<MovieListResponse>
}