package com.example.movie_db_app.ui.trending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentRegisterBinding
import com.example.movie_db_app.databinding.FragmentTrendingBinding
import com.example.movie_db_app.ui.MovieListAdapter
import com.example.movie_db_app.ui.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment: Fragment() {

    private val movieListViewModel by viewModel<MovieListViewModel>()
    private var movieListAdapter: MovieListAdapter? = null
    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromApi()
        setObservers()
    }

    fun getDataFromApi() {
        movieListViewModel.getTrendingMovies()
        movieListViewModel.getGenres()
    }

    fun setObservers() {
        movieListViewModel.moviesData.observe(viewLifecycleOwner, Observer {
            println(it[0])
            setMovieListAdapter(it)
            movieListAdapter?.notifyDataSetChanged()
            println("INDIVIDUAL_MOVIE")
        })

        movieListViewModel.genresData.observe(viewLifecycleOwner, Observer {
            println(it[0])
            println(it)
            Log.i(it.toString(), "GENRES_LIST")
        })
    }

    private fun setMovieListAdapter(movieList: List<MovieItemResponse>) {
        movieListAdapter = MovieListAdapter(requireContext(), movieList)
        binding.rcMoviesList.adapter = movieListAdapter
    }

}