package com.example.movie_db_app.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.repository.MoviesRepo
import com.example.movie_db_app.data.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val moviesRepo: MoviesRepo, private val userRepo: UserRepo) : ViewModel() {

    var moviesData = MutableLiveData<List<Movie>>()
    private val cs = CoroutineScope(Dispatchers.IO)

    fun getFavoriteMovies() {
        cs.launch {
            moviesData.postValue(moviesRepo.getFavoriteMovies(userRepo.CURRENT_USER!!))
        }
    }
}