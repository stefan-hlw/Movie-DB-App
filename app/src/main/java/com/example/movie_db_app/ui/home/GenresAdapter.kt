package com.example.movie_db_app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.databinding.ItemGenreBinding
import com.example.movie_db_app.utils.Constants


class GenresAdapter(
    private val context: Context,
    private val genresList: List<GenresDbModel>
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private var _binding: ItemGenreBinding? = null
    private val binding get() = _binding!!
    private var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_resources)
        holder.itemView.startAnimation(animation)
        holder.bindView(genresList[position])
    }

    override fun getItemCount(): Int {
        return genresList.size
    }


    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(genres: GenresDbModel) {
            binding.genreTitle.text = genres.name

            Glide.with(context)
                .load(getCategoryUrl(genres.name))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.genreImage)
            binding.genreCard.setOnClickListener {
                onItemClickListener?.openCategory(genres.name)
            }
        }
    }

    interface OnItemClickListener {
        fun openCategory(category: String?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getCategoryUrl(category: String?): String {
        when(category?.lowercase()) {
            "comedy" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_COMEDY
            }
            "action" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_ACTION
            }
            "adventure" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_ADVENTURE
            }
            "animation" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_ANIMATION
            }
            "crime" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_CRIME
            }
            "documentary" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_DOCUMENTARY
            }
            "drama" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_DRAMA
            }
            "family" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_FAMILY
            }
            "fantasy" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_FANTASY
            }
            "history" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_HISTORY
            }
            "horror" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_HORROR
            }
            "music" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_MUSIC
            }
            "mystery" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_MYSTERY
            }
            "romance" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_ROMANCE
            }
            "war" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_WAR
            }
            "western" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_WESTERN
            }
            "thriller" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_THRILLER
            }
            "science fiction" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_SCIENCE_FICTION
            }
            "tv movie" -> {
                return Constants.IMAGE_BASE_URL + Constants.CATEGORY_TV_MOVIE
            }
        }
        return ""
    }



}