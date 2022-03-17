package com.example.movie_db_app.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Genres (

    @SerializedName("id"   )
    @Expose
    var id   : Int?    = null,
    @SerializedName("name" )
    @Expose
    var name : String? = null

)