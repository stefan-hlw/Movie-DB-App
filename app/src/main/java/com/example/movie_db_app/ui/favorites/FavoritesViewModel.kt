package com.example.movie_db_app.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.repository.MoviesRepo
import com.example.movie_db_app.data.repository.UserRepo
import kotlinx.coroutines.*

class FavoritesViewModel(private val moviesRepo: MoviesRepo, private val userRepo: UserRepo) : ViewModel() {

    var moviesData = MutableLiveData<List<Movie?>?>()
    private val cs = CoroutineScope(Dispatchers.IO)

    fun getAllFavoriteMovies() {
        moviesData.value = null
        cs.launch {
            moviesData.postValue(moviesRepo.getAllFavoriteMovies(userRepo.CURRENT_USER!!))
        }
    }
}