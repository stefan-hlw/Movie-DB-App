package com.example.movie_db_app.di

import androidx.room.Room
import com.example.movie_db_app.data.database.AppDatabase
import com.example.movie_db_app.data.remote.ServiceApi
import com.example.movie_db_app.data.repository.MovieListRepo
import com.example.movie_db_app.data.repository.MovieListRepoImpl
import com.example.movie_db_app.data.repository.UserRepo
import com.example.movie_db_app.data.repository.UserRepoImpl
import com.example.movie_db_app.ui.MovieListViewModel
import com.example.movie_db_app.ui.UserViewModel
import com.example.movie_db_app.utils.constants
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
        Room.databaseBuilder(androidContext(),
            AppDatabase::class.java,
            "database-name").build()
    }

    single {
        get<AppDatabase>().userDao()
    }

    factory<UserRepo> {
        UserRepoImpl(get())
    }

    single<MovieListRepo> {
        MovieListRepoImpl(get())
    }

    viewModel {
        UserViewModel(get())
    }

    viewModel {
        MovieListViewModel(get())
    }

    fun provideApi(retrofitBuilder : Retrofit.Builder): ServiceApi {
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

  //       Connection timeouts can be added here if desired

        val httpClient = OkHttpClient.Builder().addInterceptor {
            val oldReq = it.request()
            val newUrl = oldReq.url.newBuilder()
                .addQueryParameter("api_key", constants.API_KEY)
                .build()
            val newReq = oldReq.newBuilder().url(newUrl).build()
            it.proceed(newReq)
        }.addInterceptor(logging).build()

//        val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
            .baseUrl(constants.BASE_URL)
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