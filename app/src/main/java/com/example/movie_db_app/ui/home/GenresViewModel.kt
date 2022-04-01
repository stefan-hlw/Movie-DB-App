package com.example.movie_db_app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    private val cs = CoroutineScope(Dispatchers.IO)
    var genresDbData = MutableLiveData<List<GenresDbModel>>()

    fun getGenresFromDb() {
        cs.launch {
            genresDbData.postValue(moviesRepo.getGenresFromDb())
        }
    }

}