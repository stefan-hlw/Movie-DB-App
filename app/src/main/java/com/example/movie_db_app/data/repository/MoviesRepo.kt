package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieListResponse
import retrofit2.Response

interface MoviesRepo {
    suspend fun getTrendingMovies(): Response<MovieListResponse>
    suspend fun getGenres(): List<Genres>
}