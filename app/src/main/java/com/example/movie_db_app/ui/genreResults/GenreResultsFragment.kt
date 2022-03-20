package com.example.movie_db_app.ui.genreResults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentGenreResultsBinding
import com.example.movie_db_app.databinding.FragmentGenresBinding
import com.example.movie_db_app.databinding.FragmentTrendingBinding
import com.example.movie_db_app.ui.MovieListAdapter
import com.example.movie_db_app.ui.genres.GenresFragmentArgs
import com.example.movie_db_app.ui.trending.TrendingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreResultsFragment : Fragment(), MovieListAdapter.OnItemClickListener {

    private val genreResultsViewModel by viewModel<GenreResultsViewModel>()
    private var movieListAdapter: MovieListAdapter? = null
    private var _binding: FragmentGenreResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryFromBundle = GenresFragmentArgs.fromBundle(requireArguments()).category
        setObservers()
        getDataFromDb()
        getSearchResults(categoryFromBundle)
    }

    private fun getDataFromDb() {
        genreResultsViewModel.mapGenres()
    }

    private fun getSearchResults(category: String) {
        genreResultsViewModel.getCategoryItems(category)
    }

    private fun setObservers() {
        genreResultsViewModel.moviesData.observe(viewLifecycleOwner, Observer {
            genreResultsViewModel.convertGenreIdsToNames(it)
            setMovieListAdapter(it)
            movieListAdapter?.notifyDataSetChanged()
        })
    }

    private fun setMovieListAdapter(movieList: List<MovieItemResponse>) {
        movieListAdapter = MovieListAdapter(requireContext(), movieList)
        movieListAdapter?.setOnItemClickListener(this)
        binding.rcCategoryMoviesList.adapter = movieListAdapter
    }

    override fun openMovie(movie: MovieItemResponse) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_genreResultsFragment_to_movieDetailsFragment, bundle)
    }
}