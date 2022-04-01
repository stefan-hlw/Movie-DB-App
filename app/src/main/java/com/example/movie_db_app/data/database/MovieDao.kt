package com.example.movie_db_app.data.database

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: GenresDbModel)

    @Query("SELECT * FROM genres")
    fun getGenresFromDb(): List<GenresDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieFavorite(movieFavorite: MovieFavorite)

    @Query("SELECT * FROM movieFavorite INNER JOIN movie ON movieFavorite.movie_id = movie.id WHERE email = :email")
    suspend fun getAllFavoriteMovies(email: String) : List<Movie?>?

    @Query("SELECT COUNT(*) FROM movieFavorite INNER JOIN movie ON movieFavorite.movie_id = movie.id WHERE email = :email AND movie_id = :id")
    suspend fun isMovieFavorite(email: String, id: Int) : Int

    @Query("DELETE FROM movieFavorite WHERE email = :email AND movie_id = :id")
    suspend fun removeFavoriteMovie(email: String, id:Int)

}