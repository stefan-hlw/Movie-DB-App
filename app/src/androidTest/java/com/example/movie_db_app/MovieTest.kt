package com.example.movie_db_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.movie_db_app.data.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieTest {

    // InstantTaskExecutorRule swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var movieDao: MovieDao
    private val testMovie = Movie(
        1,
        "/dhr0q4eiRr8ltqPig32TwhPRdaD.jpg",
        "Adventure, Fantasy",
        "Frodo goes to Mt. Doom",
        "/dhr0q47d7a9w8d7aw89.jpg",
        "The Lord of the rings",
        10.0)
    private val testGenre = GenresDbModel(1, "Fantasy")
    private val testEmail = "testmail@gmail.com"
    private val testMovieFavorite = MovieFavorite(testEmail, testMovie.id!!)

    // Initializing database before each class
    @Before
    fun setupDatabase() {
        // inMemoryDatabaseBuilder creates the db instance in RAM instead of persistent storage
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = database.movieDao()
    }

    // Destroying database after test run
    @After
    fun teardownDatabase() {
        database.close()
    }

    @Test
    fun insertMovieTest() = runBlockingTest {
        movieDao.insertMovie(testMovie)
        run {
            movieDao.getAllMovies()?.contains(testMovie)?.let {
                assert(it)
            }
        }
    }

    @Test
    fun insertGenresTest() = runBlockingTest {
       movieDao.insertGenres(testGenre)
        run {
            movieDao.getGenresFromDb().contains(testGenre).let {
                assert(it)
            }
        }
    }

    @Test
    fun insertMovieFavoriteTest() = runBlockingTest {
        movieDao.insertMovieFavorite(testMovieFavorite)
        movieDao.insertMovie(testMovie)
        run {
            movieDao.getAllFavoriteMovies(testEmail)?.contains(testMovie)?.let {
                assert(it)
            }
        }
    }

    @Test
    fun isMovieFavoriteTest() = runBlockingTest {
        movieDao.insertMovieFavorite(testMovieFavorite)
        movieDao.insertMovie(testMovie)
        run {
            assert(movieDao.isMovieFavorite(testEmail,testMovieFavorite.movie_id) > 0)
        }
    }

    @Test
    fun removeFavoriteMovieTest() = runBlockingTest {
        movieDao.insertMovieFavorite(testMovieFavorite)
        movieDao.insertMovie(testMovie)
        movieDao.removeFavoriteMovie(testMovieFavorite.email, testMovieFavorite.movie_id)
        run {
            movieDao.getAllFavoriteMovies(testEmail)?.contains(testMovie)?.let {
                assert(!it)
            }
        }
    }

}