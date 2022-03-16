package com.example.movie_db_app.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.ui.MovieListAdapter
import com.example.movie_db_app.ui.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment: Fragment() {

    private val movieListViewModel by viewModel<MovieListViewModel>()
    private var movieListAdapter: MovieListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test123()
        setObservers()

    }


    fun test123() {
        movieListViewModel.getTrendingMovies()
    }

    fun setObservers() {
        movieListViewModel.moviesData.observe(viewLifecycleOwner, Observer {
            println(it[0])
            setMovieListAdapter(it)
            movieListAdapter?.notifyDataSetChanged()
            println("INDIVIDUAL_MOVIE")
        })


    }
    private fun setMovieListAdapter(movieList: List<MovieItemResponse>) {
        movieListAdapter = MovieListAdapter(requireContext(), movieList)
        view?.findViewById<RecyclerView>(R.id.rc_movies_list)?.adapter = movieListAdapter
    }





}