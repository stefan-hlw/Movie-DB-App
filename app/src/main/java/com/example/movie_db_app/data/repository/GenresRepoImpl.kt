package com.example.movie_db_app.data.repository

import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.ServiceApi

class GenresRepoImpl(
    private val serviceApi: ServiceApi): GenresRepo {

    override suspend fun getGenres(): List<Genres> {
        return serviceApi.getGenres().body()?.genres!!
    }

}