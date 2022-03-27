package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieListResponse
import retrofit2.Response

interface MoviesRepo {
    suspend fun getTrendingMovies(): Response<MovieListResponse>
    suspend fun getGenres(): List<Genres>
    suspend fun getSearchCategoryMovies(category: String): Response<MovieListResponse>
    suspend fun getCast(movieId: Int): ArrayList<Cast>?

    suspend fun getGenresFromDb(): List<GenresDbModel>
    suspend fun insertGenre(genre: GenresDbModel)

    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovieFavorite(email: String, movie_id: Int)
    suspend fun getFavoriteMovies(email: String) : List<Movie>

}