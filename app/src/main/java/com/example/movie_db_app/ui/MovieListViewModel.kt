package com.example.movie_db_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.MovieListResponse
import com.example.movie_db_app.data.repository.MovieListRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class MovieListViewModel(
    private val movieListRepo: MovieListRepo): ViewModel() {

    fun getTrendingMovies(): LiveData<Response<MovieListResponse>> {
        println("MovieListViewModel BEFORE")
        return movieListRepo.getTrendingMovies()
    }

}
