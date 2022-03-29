package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.remote.MovieListResponse
import retrofit2.Response

interface MoviesRepo {
    var trendingMoviesCache : List<MovieItemResponse>?
    var genresCache : List<Genres>?
    var genresMap : Map<String?, String?>

    suspend fun getTrendingMovies(): Response<MovieListResponse>
    suspend fun getGenres(): List<Genres>
    suspend fun getSearchCategoryMovies(category: String): Response<MovieListResponse>
    suspend fun getCast(movieId: Int): ArrayList<Cast>?

    suspend fun getGenresFromDb(): List<GenresDbModel>
    suspend fun insertGenre(genre: GenresDbModel)

    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovieFavorite(email: String, movie_id: Int)
    suspend fun getAllFavoriteMovies(email: String) : List<Movie?>?
    suspend fun isMovieFavorite(email: String, id: Int) : Boolean?
    suspend fun removeFavoriteMovie(email: String, id:Int)
}