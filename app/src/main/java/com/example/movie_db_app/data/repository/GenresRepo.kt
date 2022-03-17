package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.remote.Genres

interface GenresRepo {
    suspend fun getGenres(): List<Genres>
}