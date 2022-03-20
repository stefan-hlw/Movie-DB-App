package com.example.movie_db_app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genres")
data class GenresDbModel (
    @PrimaryKey
    @SerializedName("id"   )
    @Expose
    var id   : Int,
    @SerializedName("name" )
    @Expose
    var name : String? = null
)
