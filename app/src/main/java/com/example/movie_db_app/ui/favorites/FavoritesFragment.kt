package com.example.movie_db_app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentFavoritesBinding
import com.example.movie_db_app.ui.MovieListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

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
        setActionBar()

        favoritesViewModel.getFavoriteMovies()
        setObservers()
    }

    private fun setObservers() {
        favoritesViewModel.moviesData.observe(viewLifecycleOwner, Observer {
            println(it)
            println("FAVORITE_MOVIE_DATA")
        })
    }

    private fun setActionBar() {
        binding.actionBarFavorites.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_editProfileFragment)
        }
        binding.actionBarFavorites.actionBarTopText.text = getString(R.string.favorites)
    }

    private fun setMovieListAdapter(movieList: List<MovieItemResponse>) {
        movieListAdapter = MovieListAdapter(movieList)
//        movieListAdapter?.setOnItemClickListener(this)
        binding.rcMoviesList.adapter = movieListAdapter
    }

    // TODO Figure out what to do with MovieItemResponse and Movie, rn creating another Adapter seems like the best solution
}