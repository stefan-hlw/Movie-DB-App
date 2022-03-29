package com.example.movie_db_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movie_db_app.ui.MovieDetails.MovieDetailsViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {


    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(3000)

            setObservers()

            splashViewModel.getGenresFromApi()
            splashViewModel.getTrendingMovies()
            splashViewModel.mapGenres()

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setObservers() {
        splashViewModel.genresMap.observe(this, Observer {
            if(it.isNotEmpty()) {
                splashViewModel.saveMappedGenres()
            }
        })

        splashViewModel.genresData.observe(this, Observer {
            if(it != null) {
                splashViewModel.insertGenresIntoDb()
            }
        })
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}