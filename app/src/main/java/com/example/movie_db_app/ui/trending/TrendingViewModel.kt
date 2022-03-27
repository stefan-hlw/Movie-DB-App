package com.example.movie_db_app.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    var moviesData = MutableLiveData<List<MovieItemResponse>>()
    private var genresData = MutableLiveData<List<Genres>?>()
    private var genresMap = MutableLiveData<Map<String?, String?>>()

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getTrendingMovies() {
        cs.launch {
            moviesData.postValue(moviesRepo.getTrendingMovies().body()?.results!!)
        }
    }

    fun getGenresFromApi() {
        cs.launch {
            genresData.postValue(moviesRepo.getGenres())
        }
        mapGenres()
        insertGenresIntoDb()
    }

    private fun insertGenresIntoDb() {
        cs.launch {
            genresData.value?.forEach {
                moviesRepo.insertGenre(GenresDbModel(it.id!!.toInt(), it.name))
            }
        }
    }

    private fun mapGenres() {
        cs.launch {
            val s = moviesRepo.getGenres().map {
                it.id to it.name
            }.toMap()
            genresMap.postValue(s)
        }
    }

    private fun mapGenresFromIds(ids: List<String?>): ArrayList<String?> {
        val genres = ArrayList<String?>()
        for (i in ids) {
            val currentId = genresMap.value?.filter {
                it.key == i
            }
            if (currentId != null && currentId.keys.isNotEmpty()) {
                genres.add(currentId.getValue(i))
            }
        }
        return genres
    }

    fun convertGenreIdsToNames(item: List<MovieItemResponse>) {
        for (i in item) {
            val genreNames: ArrayList<String?> = mapGenresFromIds(i.genreIds!!)
            i.genreIds = genreNames.toList()
        }
    }
}

