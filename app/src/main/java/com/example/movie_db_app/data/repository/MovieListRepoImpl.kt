package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.remote.MovieListResponse
import com.example.movie_db_app.data.remote.ServiceApi
import retrofit2.Response

class MovieListRepoImpl(
    private val serviceApi: ServiceApi): MovieListRepo {

    override suspend fun getTrendingMovies(): Response<MovieListResponse> {
        return serviceApi.getTrendingMovies()
    }

}