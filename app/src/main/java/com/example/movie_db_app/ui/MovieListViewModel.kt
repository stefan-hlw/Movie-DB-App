package com.example.movie_db_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.MovieListResponse
import com.example.movie_db_app.data.repository.MovieListRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieListViewModel(
    private val movieListRepo: MovieListRepo): ViewModel() {

    var trendingMoviesLiveData = MutableLiveData<Response<MovieListResponse>>()

    fun getTrendingMovies() {
        viewModelScope.launch {
            trendingMoviesLiveData.value = movieListRepo.getTrendingMovies()
        }
    }



}
