package com.example.movie_db_app.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    @Expose
    val page: Int?,
    @SerializedName("results")
    @Expose
    var results: List<MovieItemResponse>? = null,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int?
)