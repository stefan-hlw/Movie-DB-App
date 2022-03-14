package com.example.movie_db_app.data.repository

import androidx.lifecycle.LiveData
import com.example.movie_db_app.data.remote.MovieListResponse
import retrofit2.Response

interface MovieListRepo {
    fun getTrendingMovies(): LiveData<Response<MovieListResponse>>
}