package com.example.movie_db_app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.ItemMovieBinding
import com.example.movie_db_app.ui.genres.GenresAdapter
import com.example.movie_db_app.utils.Constants


class MovieListAdapter(
    private val context: Context,
    private val movieList: List<MovieItemResponse>
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var _binding: ItemMovieBinding? = null
    private val binding get() = _binding!!
    private var onItemClickListener: MovieListAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_resources)
        holder.itemView.startAnimation(animation)
        holder.bindView(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(movies: MovieItemResponse) {
            binding.title.text = movies.title
            binding.rating.text = movies.voteAverage.toString()
            binding.genre.text = movies.genreIds.toString().dropLast(1).drop(1)
            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + movies.posterPath.toString())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.poster)
            binding.cardLinear.setOnClickListener {
                onItemClickListener?.openMovie(movies)
            }
        }
    }

    interface OnItemClickListener {
        fun openMovie(movie: MovieItemResponse)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


}