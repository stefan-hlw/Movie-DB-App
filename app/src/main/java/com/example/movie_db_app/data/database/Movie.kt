package com.example.movie_db_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(@PrimaryKey(autoGenerate = false)
                 @ColumnInfo(name = "id"                    ) val id                  : Int?                           = null,
                 @ColumnInfo(name = "backdrop_path"         ) val backdropPath        : String?                        = null,
                 @ColumnInfo(name = "genres"                ) val genres              : List<Int>                      = arrayListOf(),
                 @ColumnInfo(name = "overview"              ) val overview            : String?                        = null,
                 @ColumnInfo(name = "poster_path"           ) val posterPath          : String?                        = null,
                 @ColumnInfo(name = "title"                 ) val title               : String?                        = null,
                 @ColumnInfo(name = "vote_average"          ) val voteAverage         : Double?                        = null,
)