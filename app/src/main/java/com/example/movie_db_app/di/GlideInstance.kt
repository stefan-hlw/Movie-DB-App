package com.example.movie_db_app.di

import com.bumptech.glide.RequestManager


class GlideInstance constructor(private val requestManager: RequestManager) {
   fun getGlideInstance() : RequestManager {
       return requestManager
   }
}