package com.example.movie_db_app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Genres (
    @PrimaryKey val id:           Int,
                val name:        String
)
