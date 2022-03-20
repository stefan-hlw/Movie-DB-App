package com.example.movie_db_app.ui.MovieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val moviesRepo: MoviesRepo): ViewModel() {

    var castData = MutableLiveData<ArrayList<Cast>>()

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getCast(movieId: Int) {
        cs.launch {
            castData.postValue(moviesRepo.getCast(movieId))
        }
    }

}