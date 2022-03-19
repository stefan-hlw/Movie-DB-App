package com.example.movie_db_app.di

import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.AppDatabase
import com.example.movie_db_app.data.remote.ServiceApi
import com.example.movie_db_app.data.repository.*
import com.example.movie_db_app.ui.MovieListViewModel
import com.example.movie_db_app.ui.UserViewModel
import com.example.movie_db_app.utils.constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
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

    factory<UserRepo> {
        UserRepoImpl(get())
    }

    single<MoviesRepo> {
        MovieListRepoImpl(get())
    }

    viewModel {
        UserViewModel(get())
    }

    viewModel {
        MovieListViewModel(get())
    }

    //Glide setup

//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.placeholder_image)
//            .error(R.drawable.placeholder_image)
//    }
//
//    single(named("RQO")) { provideRequestOptions() }
//
//    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
//        return Glide.with(application).setDefaultRequestOptions(requestOptions)
//    }
//
//    single(named("GLI")) { provideGlideInstance(androidApplication() as Application, get(named("RQO"))) }
//
//    factory {
//        GlideInstance(get(named("GLI")))
//    }

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
                .addQueryParameter("api_key", constants.API_KEY)
                .build()
            val newReq = oldReq.newBuilder().url(newUrl).build()
            it.proceed(newReq)
        }.addInterceptor(logging).build()

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