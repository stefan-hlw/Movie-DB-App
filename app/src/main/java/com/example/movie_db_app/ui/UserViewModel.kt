package com.example.movie_db_app.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.data.repository.UserRepo

class UserViewModel(
    private val userRepo: UserRepo
) : ViewModel() {


    fun createUser(user: User) {
        userRepo.createUser(user)
    }

    fun getUser(email: String, password: String): LiveData<User?> {
        return userRepo.getUser(email, password)
    }

    fun setCurrentUser(email: String) {
        userRepo.CURRENT_USER = email
        Log.i(userRepo.CURRENT_USER.toString(), "CURRENT_USER")
    }


}