package com.example.movie_db_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.*

class SplashViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    val operation = viewModelScope.async {
                listOf(
                    async { moviesRepo.getGenres() },
                    async { moviesRepo.getTrendingMovies()}
                ).joinAll()
            }


}