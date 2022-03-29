package com.example.movie_db_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    var genresData = MutableLiveData<List<Genres>?>()
    var genresMap = MutableLiveData<Map<String?, String?>>()

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getTrendingMovies() {
        cs.launch {
            moviesRepo.trendingMoviesCache = moviesRepo.getTrendingMovies().body()?.results!!
        }
    }

    fun getGenresFromApi() {
        cs.launch {
            genresData.postValue(moviesRepo.getGenres())
        }
    }

    fun insertGenresIntoDb() {
        cs.launch {
            genresData.value?.forEach {
                moviesRepo.insertGenre(GenresDbModel(it.id!!.toInt(), it.name))
            }
        }
    }

    fun mapGenres() {
        cs.launch {
            val s = moviesRepo.getGenres().map {
                it.id to it.name
            }.toMap()
            genresMap.postValue(s)
        }
    }

    fun saveMappedGenres() {
        moviesRepo.genresMap = genresMap.value!!
    }

}