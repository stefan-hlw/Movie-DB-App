package com.example.movie_db_app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.repository.GenresRepo
import com.example.movie_db_app.data.repository.MovieListRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieListRepo: MovieListRepo,
    private val genresRepo: GenresRepo
) : ViewModel() {

    var moviesData = MutableLiveData<List<MovieItemResponse>>()
    var genresData = MutableLiveData<List<Genres>>()
    private val cs = CoroutineScope(Dispatchers.IO)

    fun getTrendingMovies() {
        cs.launch {
            moviesData.postValue(movieListRepo.getTrendingMovies().body()?.results!!)
        }
    }

    fun getGenres() {
        cs.launch {
            genresData.postValue(genresRepo.getGenres())
        }
    }


}
