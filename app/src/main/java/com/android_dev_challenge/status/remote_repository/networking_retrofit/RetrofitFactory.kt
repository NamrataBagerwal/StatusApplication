package com.android_dev_challenge.status.remote_repository.networking_retrofit

import com.android_dev_challenge.status.remote_repository.networking_retrofit.interceptor.CacheInterceptor
import com.android_dev_challenge.status.remote_repository.networking_retrofit.interceptor.OfflineCacheInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


object RetrofitFactory {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(CacheInterceptor())
        .addInterceptor(OfflineCacheInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

}