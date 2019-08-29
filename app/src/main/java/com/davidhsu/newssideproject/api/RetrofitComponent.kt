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

    private const val NEWS_BASE_URL = "https://newsapi.org/v2/"
    private const val WEATHER_BASE_URL = "https://opendata.cwb.gov.tw/api/v1/"

    private lateinit var NewsRetrofit: Retrofit
    private lateinit var WeatherRetrofit: Retrofit

    fun getNewsInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()

        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        NewsRetrofit = Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return NewsRetrofit
    }

    fun getWeatherInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()

        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        WeatherRetrofit = Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return WeatherRetrofit
    }

}