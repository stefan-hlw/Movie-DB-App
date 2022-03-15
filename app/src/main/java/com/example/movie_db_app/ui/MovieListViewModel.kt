package com.example.movie_db_app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.repository.MovieListRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieListRepo: MovieListRepo
) : ViewModel() {

    var moviesData = MutableLiveData<List<MovieItemResponse>>()
    private val cs = CoroutineScope(Dispatchers.IO)

    fun getTrendingMovies() {
        cs.launch {
            moviesData.postValue(movieListRepo.getTrendingMovies().body()?.results!!)
        }

    }

}
