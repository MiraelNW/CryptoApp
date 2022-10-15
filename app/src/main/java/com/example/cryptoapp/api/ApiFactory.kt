package com.example.cryptoapp.api

import io.reactivex.plugins.RxJavaPlugins
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create

object  ApiFactory {
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
     const val BASE_IMAGE_URL = "https://cryptocompare.com/"
    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val apiService = retrofit.create(ApiService::class.java)
}