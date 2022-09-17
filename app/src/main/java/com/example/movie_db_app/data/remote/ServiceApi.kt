package com.example.movie_db_app.data.remote

import com.example.movie_db_app.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET(Constants.TRENDING_MOVIES)
    suspend fun getTrendingMovies(): Response<MovieListResponse>

    @GET(Constants.GENRES)
    suspend fun getGenres(): Response<GenresResponse>

    @GET(Constants.SEARCH)
    suspend fun getSearchCategoryMovies(@Query("query") category: String): Response<MovieListResponse>

    @GET(Constants.CAST)
    suspend fun getCast(@Path("movieId")movieId: Int): Response<CastResponse>

}