package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.database.MovieDao
import com.example.movie_db_app.data.remote.*
import retrofit2.Response

class MoviesRepoImpl(
    private val serviceApi: ServiceApi, private val movieDao: MovieDao): MoviesRepo {

    override suspend fun getTrendingMovies(): Response<MovieListResponse> {
        return serviceApi.getTrendingMovies()
    }

    override suspend fun getGenres(): List<Genres> {
        return serviceApi.getGenres().body()?.genres!!
    }

    override suspend fun getGenresFromDb(): List<GenresDbModel> {
        return movieDao.getGenresFromDb()
    }

    override suspend fun getCast(movieId: Int): ArrayList<Cast>? {
        return serviceApi.getCast(movieId).body()?.cast
    }

    override suspend fun getSearchCategoryMovies(category: String): Response<MovieListResponse> {
        return serviceApi.getSearchCategoryMovies(category)
    }



    override suspend fun insertGenre(genre: GenresDbModel) {
        movieDao.insertGenres(genre)
    }

    // local private val TRENDING_MOVIES_CACHE for storing api response

}