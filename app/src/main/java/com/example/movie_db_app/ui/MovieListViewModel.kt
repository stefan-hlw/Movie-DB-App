package com.example.movie_db_app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    var moviesData = MutableLiveData<List<MovieItemResponse>>()
    var genresData = MutableLiveData<List<Genres>>()
    private val cs = CoroutineScope(Dispatchers.IO)

    fun getTrendingMovies() {
        cs.launch {
            moviesData.postValue(moviesRepo.getTrendingMovies().body()?.results!!)
        }
    }

    fun getGenres() {
        cs.launch {
            genresData.postValue(moviesRepo.getGenres())
        }
    }


}
