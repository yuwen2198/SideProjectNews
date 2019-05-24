package com.davidhsu.newssideproject.api

import com.davidhsu.newssideproject.api.model.ResponseNewsData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author : DavidHsu on 2019/03/26
 *
 */
interface NewsApi {

    @GET("top-headlines")
    fun getCompositeNews(@Query("country") country: String,@Query("apiKey") apiKey: String): Call<ResponseNewsData>

    @GET("top-headlines")
    fun getCategoryNews(@Query("country") country: String, @Query("category") category: String, @Query("apiKey") apiKey: String): Call<ResponseNewsData>

    @GET("top-headlines")
    fun getCompositeNewsWithRx(@Query("country") country: String,@Query("apiKey") apiKey: String): Observable<ResponseNewsData>

}