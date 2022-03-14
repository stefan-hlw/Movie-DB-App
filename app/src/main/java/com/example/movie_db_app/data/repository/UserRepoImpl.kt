package com.example.movie_db_app.data.repository

import androidx.lifecycle.LiveData
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.data.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserRepoImpl(private val userDao: UserDao): UserRepo {

    override fun createUser(user: User) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                userDao.createUser(user)
            }
        }
    }


    override fun editUser(user: User) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                userDao.editUser(user)
            }
        }
    }

    override fun getUser(email: String, password: String): LiveData<User> {
        return userDao.getUserInfo(email, password)
    }


    override fun doesUserExist(email: String): LiveData<Int> {
        return userDao.doesUserExist(email)
    }
}