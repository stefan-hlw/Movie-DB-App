package com.example.movie_db_app.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenresResponse (

    @SerializedName("genres" )
    @Expose
    var genres : List<Genres> = listOf()

)
