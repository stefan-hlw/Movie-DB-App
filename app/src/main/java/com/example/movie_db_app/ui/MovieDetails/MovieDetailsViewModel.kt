package com.example.movie_db_app.ui.MovieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.data.repository.MoviesRepo
import com.example.movie_db_app.data.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val moviesRepo: MoviesRepo,
                            private val userRepo: UserRepo): ViewModel() {

    var castData = MutableLiveData<ArrayList<Cast>>()

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getCast(movieId: Int) {
        cs.launch {
            castData.postValue(moviesRepo.getCast(movieId))
        }
    }

    fun favoriteMovie(movie: Movie) {
        cs.launch {
            moviesRepo.insertMovie(movie)
            moviesRepo.insertMovieFavorite(userRepo.CURRENT_USER!!, movie.id!!)
        }
    }

}