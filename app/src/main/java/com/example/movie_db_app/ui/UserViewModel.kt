package com.example.movie_db_app.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.data.repository.UserRepo
import com.example.movie_db_app.utils.constants.EMAIL

class UserViewModel(
    private val userRepo: UserRepo) : ViewModel() {


    fun createUser(email: String, password: String, fullName: String, birthday: String?) {
        val user = User(null, email, password, fullName, birthday)
        userRepo.createUser(user)
        print(userRepo.getUser(email, password))
    }

    fun editUser(user: User) {
        userRepo.editUser(user)
    }

    fun getUser(email: String, password: String): LiveData<User> {
        return userRepo.getUser(email, password)
    }

    fun doesUserExist(email: String): LiveData<Int> {
        return userRepo.doesUserExist(email)
    }


}