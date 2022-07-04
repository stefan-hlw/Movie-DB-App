package com.example.movie_db_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.movie_db_app.data.database.AppDatabase
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.data.database.UserDao
import com.example.movie_db_app.utils.getOrAwaitValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private val testUser = User("testmail@gmail.com", "Test1234!", "Stefan Vucan", "17-04-1996")


    // Initializing database before each class
    @Before
    fun setupDatabase() {
        // inMemoryDatabaseBuilder creates the db instance in RAM instead of persistent storage
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
    }

    // Destroying database after test run
    @After
    fun teardownDatabase() {
        database.close()
    }

    @Test
    fun createUserTest() = runBlockingTest {
        val testUser = User("testmail@gmail.com", "Test1234!", "Stefan Vucan", "17-04-1996")
        userDao.createUser(testUser)
        val user = userDao.getUserProfile(testUser.email).getOrAwaitValue()
        Assert.assertTrue(user == testUser)
    }


    @Test
    fun editUserTest() = runBlockingTest {
        val editedUser = User("testmail@gmail.com", "Test1234!", "Edited Name", "17-04-1996")
        userDao.createUser(testUser)
        userDao.editUser(editedUser)
        val user = userDao.getUserProfile(testUser.email).getOrAwaitValue()
        Assert.assertTrue(user == editedUser)
    }

    @Test
    fun getUserProfileUserTest() = runBlockingTest {
        userDao.createUser(testUser)
        val user = userDao.getUserProfile(testUser.email).getOrAwaitValue()
        Assert.assertTrue(user == testUser)
    }

    @Test
    fun getUserInfoTest() = runBlockingTest {
        userDao.createUser(testUser)
        val user = userDao.getUserInfo(testUser.email, testUser.password).getOrAwaitValue()
        Assert.assertTrue(user == testUser)
    }

}
