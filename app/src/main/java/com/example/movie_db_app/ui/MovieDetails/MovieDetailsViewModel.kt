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
    var isMovieFavorite = MutableLiveData<Boolean>(false)

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getCast(movieId: Int) {
        cs.launch {
            castData.postValue(moviesRepo.getCast(movieId))
        }
    }

    fun checkIsMovieFavorite(id: Int) {
        cs.launch {
            isMovieFavorite.postValue(moviesRepo.isMovieFavorite(userRepo.CURRENT_USER!!, id))
        }
    }

    fun onFavoriteClick(movie: Movie) {
        if(isMovieFavorite.value == true) {
            removeFavoriteMovie(movie.id!!)
        } else {
            favoriteMovie(movie)
        }
    }

    private fun favoriteMovie(movie: Movie) {
        cs.launch {
            moviesRepo.insertMovie(movie)
            moviesRepo.insertMovieFavorite(userRepo.CURRENT_USER!!, movie.id!!)
            isMovieFavorite.postValue(true)
        }
    }

    private fun removeFavoriteMovie(id:Int) {
        cs.launch {
            moviesRepo.removeFavoriteMovie(userRepo.CURRENT_USER!!, id)
            isMovieFavorite.postValue(false)
        }
    }

}