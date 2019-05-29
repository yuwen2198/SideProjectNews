package com.davidhsu.newssideproject.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
object RetrofitComponent {

    private const val BASE_URL = "https://newsapi.org/v2/"
    private lateinit var retrofit: Retrofit

    fun getInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()

        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit
    }

}