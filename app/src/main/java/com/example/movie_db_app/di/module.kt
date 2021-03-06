package com.example.movie_db_app.di

import androidx.room.Room
import com.example.movie_db_app.SplashViewModel
import com.example.movie_db_app.data.database.AppDatabase
import com.example.movie_db_app.data.remote.ServiceApi
import com.example.movie_db_app.data.repository.*
import com.example.movie_db_app.ui.MovieDetails.MovieDetailsViewModel
import com.example.movie_db_app.ui.home.TrendingViewModel
import com.example.movie_db_app.ui.authorization.UserViewModel
import com.example.movie_db_app.ui.home.FavoritesViewModel
import com.example.movie_db_app.ui.genreResults.GenreResultsViewModel
import com.example.movie_db_app.ui.home.GenresViewModel
import com.example.movie_db_app.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val applicationModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database-name"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().userDao()
    }
    single {
        get<AppDatabase>().movieDao()
    }

    single<UserRepo> {
        UserRepoImpl(get())
    }

    single<MoviesRepo> {
        MoviesRepoImpl(get(), get())
    }

    viewModel {
        UserViewModel(get())
    }

    viewModel {
        TrendingViewModel(get())
    }

    viewModel {
        GenresViewModel(get())
    }

    viewModel {
        GenreResultsViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get(), get())
    }

    viewModel {
        FavoritesViewModel(get(), get())
    }

    viewModel {
        SplashViewModel(get())
    }

    // Retrofit setup

    fun provideApi(retrofitBuilder: Retrofit.Builder): ServiceApi {
        return retrofitBuilder
            .build()
            .create(ServiceApi::class.java)
    }

    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLenient()
            .create()
    }


    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Connection timeout parameters can be added here if desired

        val httpClient = OkHttpClient.Builder().addInterceptor {
            val oldReq = it.request()
            val newUrl = oldReq.url.newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()
            val newReq = oldReq.newBuilder().url(newUrl).build()
            it.proceed(newReq)
        }.addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    single(named("API")) {
        provideRetrofitBuilder(get())
    }

    single {
        provideGsonBuilder()
    }

    single {
        provideApi(get(named("API")))
    }


}