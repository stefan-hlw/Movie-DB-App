package com.example.movie_db_app.data.repository

import androidx.lifecycle.LiveData
import com.example.movie_db_app.data.database.User

interface UserRepo {

    var CURRENT_USER: String?
    fun createUser(user: User)
    fun editUser(user: User)
    fun getUser(email: String, password: String): LiveData<User?>
    fun getUserProfile(email: String?): LiveData<User?>
}