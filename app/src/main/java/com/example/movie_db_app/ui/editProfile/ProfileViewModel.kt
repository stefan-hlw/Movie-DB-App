package com.example.movie_db_app.ui.editProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.data.repository.UserRepo

class ProfileViewModel(
    private val userRepo: UserRepo
) : ViewModel() {


    fun editUser(user: User) {
        userRepo.editUser(user)
    }

    fun getUserProfile(): LiveData<User?> {
        Log.i(userRepo.CURRENT_USER, "CURRENT_USER")
        return userRepo.getUserProfile(userRepo.CURRENT_USER)
    }

}