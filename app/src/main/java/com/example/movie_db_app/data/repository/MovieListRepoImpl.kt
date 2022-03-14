package com.example.movie_db_app.data.repository

import androidx.lifecycle.LiveData
import com.example.movie_db_app.data.remote.MovieListResponse
import com.example.movie_db_app.data.remote.ServiceApi
import retrofit2.Response

class MovieListRepoImpl(
    private val serviceApi: ServiceApi): MovieListRepo {

    override fun getTrendingMovies(): LiveData<Response<MovieListResponse>> {
        return serviceApi.getTrendingMovies()
    }
}