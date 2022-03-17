package com.example.movie_db_app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_db_app.R
import com.example.movie_db_app.data.remote.MovieItemResponse
import com.example.movie_db_app.utils.constants


class MovieListAdapter(private val context: Context,
                       private val movieList: List<MovieItemResponse>):
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(movies: MovieItemResponse) {
            itemView.findViewById<TextView>(R.id.title).text = movies.title
            itemView.findViewById<TextView>(R.id.rating).text = movies.voteAverage.toString()
            itemView.findViewById<TextView>(R.id.genre).text = movies.genreIds!![0].toString()
            Glide.with(context)
                .load(constants.IMAGE_BASE_URL + movies.posterPath.toString())
                .into(itemView.findViewById(R.id.poster))
        }
    }




}