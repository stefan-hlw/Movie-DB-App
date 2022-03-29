package com.example.movie_db_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, GenresDbModel::class, Movie::class, MovieFavorite::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
}