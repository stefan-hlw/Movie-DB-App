package com.example.movie_db_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentTrendingBinding
import com.example.movie_db_app.ui.MovieListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment(), MovieListAdapter.OnItemClickListener {

    private val trendingViewModel by viewModel<TrendingViewModel>()
    private var movieListAdapter: MovieListAdapter? = null
    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableBackButton()
        trendingViewModel.getMappedGenres()

        setActionBar()
        setObservers()
    }

    private fun setObservers() {
        trendingViewModel.trendingMoviesCache.observe(viewLifecycleOwner, Observer {
            trendingViewModel.convertGenreIdsToNames(it!!)
            setMovieListAdapter(it)
            movieListAdapter?.notifyDataSetChanged()
        })

        trendingViewModel.genresMap.observe(viewLifecycleOwner, Observer {
            trendingViewModel.getCachedMovies()
        })
    }

    private fun setActionBar() {
        binding.actionBarTrending.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_trendingFragment_to_editProfileFragment)
        }
        binding.actionBarTrending.actionBarTopText.text = getString(R.string.trending)
    }

    override fun openMovie(movie: MovieItemResponse) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_trendingFragment_to_movieDetailsFragment, bundle)
    }

    private fun setMovieListAdapter(movieList: List<MovieItemResponse>) {
        movieListAdapter = MovieListAdapter(movieList)
        movieListAdapter?.setOnItemClickListener(this)
        binding.rcMoviesList.adapter = movieListAdapter
    }


    private fun disableBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // this disables the Android native back button
                }
            }
        )
    }

}