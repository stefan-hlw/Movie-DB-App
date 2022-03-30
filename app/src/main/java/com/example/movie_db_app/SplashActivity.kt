package com.example.movie_db_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.movie_db_app.ui.MovieDetails.MovieDetailsViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       lifecycleScope.launch{
           splashViewModel.operation.await()
       }

        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

}