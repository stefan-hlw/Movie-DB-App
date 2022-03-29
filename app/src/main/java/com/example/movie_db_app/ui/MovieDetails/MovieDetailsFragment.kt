package com.example.movie_db_app.ui.MovieDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.FragmentMovieDetailsBinding
import com.example.movie_db_app.ui.favorites.FavoritesFragment
import com.example.movie_db_app.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    private val movieDetailsViewModel by viewModel<MovieDetailsViewModel>()
    private var movieDetailsAdapter: MovieDetailsAdapter? = null
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailsViewModel.checkIsMovieFavorite(MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie!!.id!!)
        movieDetailsViewModel.getCast(MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie?.id!!)
        setObservers()
        populateUI()
        setListeners()
    }

    private fun setObservers() {
        movieDetailsViewModel.castData.observe(viewLifecycleOwner, Observer {
            setMovieDetailsAdapter(it)
            movieDetailsAdapter?.notifyDataSetChanged()
        })

        movieDetailsViewModel.isMovieFavorite.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.favorite.setImageResource(R.drawable.ic_star_full)
            } else {
                binding.favorite.setImageResource(R.drawable.ic_star_outline)
            }
        })
    }

    private fun setListeners() {
        val movie: MovieItemResponse = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie!!

        val movieFormatted = Movie(
            movie.id,
            movie.backdropPath,
            movie.genreIds.toString(),
            movie.overview,
            movie.posterPath,
            movie.title,
            movie.voteAverage
        )
        binding.favorite.setOnClickListener {
            movieDetailsViewModel.onFavoriteClick(movieFormatted)
        }
    }

    private fun populateUI() {
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        binding.title.text = movie?.title
        Log.i(movie?.title, "MOVIE_TITLE")
        binding.expandTextView.text = movie?.overview
        binding.genres.text = movie?.genreIds.toString().drop(1).dropLast(1)
        binding.rating.text = movie?.voteAverage.toString()
        Glide.with(requireContext())
            .load(Constants.IMAGE_BASE_URL + movie?.backdropPath)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(binding.ivBackdrop)
    }

    private fun setMovieDetailsAdapter(castList: ArrayList<Cast>) {
        movieDetailsAdapter = MovieDetailsAdapter(castList)
        binding.rcCast.adapter = movieDetailsAdapter
    }

}