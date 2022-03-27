package com.example.movie_db_app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createUser(user: User)

    @Update
    suspend fun editUser(user: User)

    @Query("SELECT * FROM user WHERE email= :email AND password= :password")
    fun getUserInfo(email: String, password: String): LiveData<User?>

    @Query("SELECT * FROM user WHERE email= :email")
    fun getUserProfile(email: String?): LiveData<User?>

}