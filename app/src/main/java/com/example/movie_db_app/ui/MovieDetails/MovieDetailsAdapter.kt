package com.example.movie_db_app.ui.MovieDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.Cast
import com.example.movie_db_app.databinding.ItemCastBinding
import com.example.movie_db_app.utils.Constants
import java.util.ArrayList

class MovieDetailsAdapter(private val context :Context,
                          private val castList:ArrayList<Cast>) : RecyclerView.Adapter<MovieDetailsAdapter.ViewHolder>() {

    private var _binding: ItemCastBinding? = null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_resources)
        holder.itemView.startAnimation(animation)
        holder.bindView(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    inner class ViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(cast: Cast) {
            binding.realName.text = cast.originalName
            binding.movieName.text = cast.character
            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + cast.profilePath)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.castImage)
        }
    }
}
