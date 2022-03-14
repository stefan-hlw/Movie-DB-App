package com.example.movie_db_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true ) val id:           Int?,
                @ColumnInfo(name = "email"      ) val email:        String,
                @ColumnInfo(name = "password"   ) val password:     String,
                @ColumnInfo(name = "full_name"  ) val full_name:    String,
                @ColumnInfo(name = "birthday"   ) val birthday:     String?,
)
