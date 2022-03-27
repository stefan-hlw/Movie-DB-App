package com.example.movie_db_app.data.database

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: GenresDbModel)

    @Query("SELECT * FROM genres")
    fun getGenresFromDb(): List<GenresDbModel>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovieFavorite(movieFavorite: MovieFavorite)

    @Query("SELECT * FROM movie INNER JOIN movieFavorite ON movie.id = movieFavorite.movie_id WHERE email = :email")
    suspend fun getFavoriteMovies(email: String) : List<Movie>

}