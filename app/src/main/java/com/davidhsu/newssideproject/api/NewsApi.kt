package com.davidhsu.newssideproject.api

import com.davidhsu.newssideproject.api.model.ResponseNewsData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
interface NewsApi {

    @GET("top-headlines?country=tw&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getCompositeNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=business&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getBusinessNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=entertainment&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getEntertainmentNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=health&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getHealthNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=science&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getScienceNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=sports&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getSportsNews(): Call<ResponseNewsData>

    @GET("top-headlines?country=tw&category=technology&apiKey=7b370eccef7d4eca8d6af86e3ad40ea5")
    fun getTechnologyNews(): Call<ResponseNewsData>

}