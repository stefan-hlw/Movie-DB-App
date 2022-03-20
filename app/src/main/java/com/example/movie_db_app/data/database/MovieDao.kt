package com.example.movie_db_app.data.database

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: GenresDbModel)

    @Query("SELECT * FROM genres")
    fun getGenresFromDb(): List<GenresDbModel>

}