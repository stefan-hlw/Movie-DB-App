package com.example.movie_db_app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.Movie
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.databinding.ItemMovieBinding
import com.example.movie_db_app.utils.Constants
import okhttp3.internal.format


class MovieListAdapter(
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
        val animation: Animation =
            AnimationUtils.loadAnimation(binding.root.context, R.anim.animation_resources)
        holder.itemView.startAnimation(animation)
        holder.bindView(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(movies: MovieItemResponse) {
            var formattedGenres: String = movies.genreIds.toString().replace("]" , "")
            formattedGenres = formattedGenres.replace("[", "")
            with(binding) {
                title.text = movies.title
                rating.text = movies.voteAverage.toString()
                genre.text = formattedGenres
                Glide.with(root.context)
                    .load(Constants.IMAGE_BASE_URL + movies.posterPath.toString())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(poster)
                cardLinear.setOnClickListener {
                    onItemClickListener?.openMovie(movies)
                }
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