package com.example.movie_db_app.ui.genreResults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.remote.Genres
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.data.repository.MoviesRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreResultsViewModel(
    private val moviesRepo: MoviesRepo
    ): ViewModel() {

    var moviesData = MutableLiveData<List<MovieItemResponse>>()
    var genres = MutableLiveData<List<Genres>?>()
    private var genresMap = MutableLiveData<Map<String, String?>>()

    private val cs = CoroutineScope(Dispatchers.IO)

    fun getCategoryItems(category: String) {
        cs.launch {
            moviesData.postValue(moviesRepo.getSearchCategoryMovies(category).body()?.results!!)
        }
    }

    fun mapGenres() {
        cs.launch {
            val s = moviesRepo.getGenresFromDb().map {
                it.id.toString() to it.name
            }.toMap()
            genresMap.postValue(s)
        }
    }

    private fun mapGenresFromIds(ids: List<String?>): ArrayList<String?> {
        val genresList = ArrayList<String?>()

        for (i in ids) {
            val currentId = genresMap.value?.filter {
                it.key == i
            }
            if (currentId != null && currentId.keys.isNotEmpty()) {
                genresList.add(currentId.getValue(i.toString()))
            }
        }
        return genresList
    }

    fun convertGenreIdsToNames(item: List<MovieItemResponse>) {
        for (i in item) {
            val genreNames: ArrayList<String?> = mapGenresFromIds(i.genreIds!!)
            i.genreIds = genreNames.toList()
        }
    }

}