package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.database.MovieDao
import com.example.movie_db_app.data.database.MovieFavorite
import com.example.movie_db_app.data.remote.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MoviesRepoImpl(
    private val serviceApi: ServiceApi, private val movieDao: MovieDao): MoviesRepo {

    override var trendingMoviesCache : List<MovieItemResponse>? = null
    override var genresCache : List<Genres>? = null
    override var genresMap : Map<String?, String?> = mapOf()

    override suspend fun getTrendingMovies(): Response<MovieListResponse> {
        return withContext(Dispatchers.IO){
            val response = serviceApi.getTrendingMovies()
            trendingMoviesCache = response.body()?.results
            response
        }
    }

    override suspend fun getGenres(): List<Genres> {
        return withContext(Dispatchers.IO){
            val genres = serviceApi.getGenres().body()?.genres
            genres?.map{
                it.id to it.name
            }?.toMap().let {
                if (it != null)
                    genresMap = it
            }
            genres?.forEach {
                insertGenre(GenresDbModel(it.id!!.toInt(), it.name))
            }
            genres ?: listOf()
        }
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

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun insertMovieFavorite(email: String, movie_id: Int) {
        val movieFavorite = MovieFavorite( email, movie_id)
        movieDao.insertMovieFavorite(movieFavorite)
    }

    override suspend fun getAllFavoriteMovies(email: String): List<Movie?>? {
        return movieDao.getAllFavoriteMovies(email)
    }

    override suspend fun isMovieFavorite(email: String, id: Int): Boolean {
        return movieDao.isMovieFavorite(email, id) > 0
    }

    override suspend fun removeFavoriteMovie(email: String, id: Int) {
        movieDao.removeFavoriteMovie(email, id)
    }

}