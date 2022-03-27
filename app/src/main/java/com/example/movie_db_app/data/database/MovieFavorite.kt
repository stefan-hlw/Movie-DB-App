package com.example.movie_db_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieFavorite")
data class MovieFavorite(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "id") val id: Int = 0,
                         @ColumnInfo(name = "email") val email: String,
                         @ColumnInfo(name = "movie_id") val movie_id: Int
)