package com.example.movie_db_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentFavoritesBinding
import com.example.movie_db_app.ui.MovieListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : BaseHomeFragment(), MovieListAdapter.OnItemClickListener {

    private val favoritesViewModel by viewModel<FavoritesViewModel>()
    private var movieListAdapter: MovieListAdapter? = null
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableBackButton()
        setActionBar()
        setMic(binding.actionBarFavorites.ivMic)
        favoritesViewModel.getAllFavoriteMovies()
        setObservers()
    }

    override fun onStart() {
        searchView.isIconified = true
        super.onStart()
    }

    private fun setObservers() {
        favoritesViewModel.moviesData.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) {
                binding.ivEmptyFavorites.visibility = GONE
                binding.tvEmptyFavorites.visibility = GONE
                setMovieListAdapter(it)
            }
        })
    }

    private fun setActionBar() {
        binding.actionBarFavorites.clProfileIconContainer.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_editProfileFragment)
        }
        binding.actionBarFavorites.actionBarTopText.text = getString(R.string.favorites)

        searchView = binding.actionBarFavorites.search
        setSearchBar(
            binding.actionBarFavorites.search,
            binding.actionBarFavorites.ivMic,
            binding.actionBarFavorites.actionBarTopText,
            R.id.action_favoritesFragment_to_genreResultsFragment)
    }

    override fun openMovie(movie: MovieItemResponse) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_favoritesFragment_to_movieDetailsFragment, bundle)
    }

    private fun setMovieListAdapter(movieList: List<Movie?>?) {
        var movieListTransformed: MutableList<MovieItemResponse>? = mutableListOf()
        if (movieList != null) {
            for (m in movieList) {
                val movieTransformation = MovieItemResponse(
                    null,
                    backdropPath = m?.backdropPath,
                    genreIds = listOf(m?.genres),
                    id = m?.id!!,
                    null,
                    null,
                    overview = m.overview,
                    null,
                    posterPath = m.posterPath,
                    null,
                    title = m.title,
                    null,
                    voteAverage = m.voteAverage,
                    null
                )
                movieListTransformed?.add(movieTransformation)
            }
        }

        movieListAdapter = movieListTransformed?.let { MovieListAdapter(it.toList()) }
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